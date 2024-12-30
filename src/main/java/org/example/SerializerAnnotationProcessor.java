package org.example;

import org.example.annotations.Serialized;
import org.example.annotations.SerializerGenerator;
import org.example.serializer.Serializer;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SupportedAnnotationTypes({
        "org.example.annotations.SerializerGenerator",
        "org.example.annotations.Serialized"
})
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class SerializerAnnotationProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> serializedClasses = roundEnv.getElementsAnnotatedWith(Serialized.class);
        Set<? extends Element> generatorAnnotations = roundEnv.getElementsAnnotatedWith(SerializerGenerator.class);

        Map<Element, List<Element>> classFields = ReflectionHelper.mapSerializableFields(serializedClasses);

        for (Element serializer : generatorAnnotations) {
            generateSerializerImplementation(serializer, classFields);
        }

        return true;
    }

    private void generateSerializerImplementation(Element serializerInterface, Map<Element, List<Element>> classFieldsMap) {
        String interfaceName = serializerInterface.getSimpleName().toString();
        String implementationName = interfaceName + "Impl";
        String packageName = processingEnv.getElementUtils().getPackageOf(serializerInterface).getQualifiedName().toString();

        try {
            JavaFileObject builderFile = processingEnv.getFiler().createSourceFile(packageName + "." + implementationName);
            try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {
                out.println("package " + packageName + ";");
                out.println();

                out.println("import " + Serializer.class.getCanonicalName() + ";");
                out.println();

                out.println("public class " + implementationName + " implements " + interfaceName + " {");
                out.println();

                generateJsonMethod(out, classFieldsMap);

                generateXmlMethod(out, classFieldsMap);

                out.println("}");
            }
        } catch (IOException e) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Помилка при генерації класу серіалізатора: " + e.getMessage());
        }
    }

    private void generateJsonMethod(PrintWriter out, Map<Element, List<Element>> classFieldsMap) {
        out.println("\t@Override");
        out.println("\tpublic String toJson(Object obj) {");
        for (Element cls : classFieldsMap.keySet()) {
            String className = ReflectionHelper.getFullClassName(cls);
            out.println("\t\tif (obj instanceof " + className + ") {");
            out.println("\t\t\treturn toJson(" + "(" + className + ") obj" + ");");
            out.println("\t\t}");
        }
        out.println("\t\tthrow new IllegalArgumentException(\"Невідома класа для серіалізації: \" + obj.getClass().getName());");
        out.println("\t}");
        out.println();

        for (Map.Entry<Element, List<Element>> entry : classFieldsMap.entrySet()) {
            Element cls = entry.getKey();
            List<Element> fields = entry.getValue();
            String className = ReflectionHelper.getFullClassName(cls);

            out.println("\tprivate String toJson(" + className + " obj) {");
            out.println("\t\tStringBuilder json = new StringBuilder();");
            out.println("\t\tjson.append(\"{\");");

            for (int i = 0; i < fields.size(); i++) {
                Element field = fields.get(i);
                String fieldName = ReflectionHelper.getSerializedName(field);
                String getter = ReflectionHelper.generateGetterName(field) + "()";
                String fieldType = field.asType().toString();

                out.print("\t\tjson.append(\"\\\"" + fieldName + "\\\": ");
                if (fieldType.equals("java.lang.String")) {
                    out.print("\\\"\" + obj." + getter + " + \"\\\"");
                } else {
                    out.print("\" + obj." + getter);
                }
                if (i < fields.size() - 1) {
                    out.println(" + \", \");");
                } else {
                    out.println(";");
                }
            }

            out.println("\t\tjson.append(\"}\");");
            out.println("\t\treturn json.toString();");
            out.println("\t}");
            out.println();
        }
    }

    private void generateXmlMethod(PrintWriter out, Map<Element, List<Element>> classFieldsMap) {
        out.println("\t@Override");
        out.println("\tpublic String toXml(Object obj) {");
        for (Element cls : classFieldsMap.keySet()) {
            String className = ReflectionHelper.getFullClassName(cls);
            Serialized annotation = cls.getAnnotation(Serialized.class);
            String modelName = annotation.name().isEmpty() ? decapitalize(cls.getSimpleName().toString()) : annotation.name();

            out.println("\t\tif (obj instanceof " + className + ") {");
            out.println("\t\t\treturn toXml(" + "(" + className + ") obj" + ", \"" + modelName + "\");");
            out.println("\t\t}");
        }
        out.println("\t\tthrow new IllegalArgumentException(\"Невідома класа для серіалізації: \" + obj.getClass().getName());");
        out.println("\t}");
        out.println();

        for (Map.Entry<Element, List<Element>> entry : classFieldsMap.entrySet()) {
            Element cls = entry.getKey();
            List<Element> fields = entry.getValue();
            String className = ReflectionHelper.getFullClassName(cls);
            Serialized annotation = cls.getAnnotation(Serialized.class);
            String modelName = annotation.name().isEmpty() ? decapitalize(cls.getSimpleName().toString()) : annotation.name();

            out.println("\tprivate String toXml(" + className + " obj, String rootName) {");
            out.println("\t\tStringBuilder xml = new StringBuilder();");
            out.println("\t\txml.append(\"<\" + rootName + \">\");");

            for (Element field : fields) {
                String fieldName = ReflectionHelper.getSerializedName(field);
                String getter = ReflectionHelper.generateGetterName(field) + "()";

                out.println("\t\txml.append(\"<" + fieldName + ">\").append(obj." + getter + ").append(\"</" + fieldName + ">\");");
            }

            out.println("\t\txml.append(\"</\" + rootName + \">\");");
            out.println("\t\treturn xml.toString();");
            out.println("\t}");
            out.println();
        }
    }

    /**
     * Приводить першу літеру рядка до нижнього регістру.
     *
     * @param str вхідний рядок
     * @return рядок з першою літерою в нижньому регістрі
     */
    private String decapitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }
}