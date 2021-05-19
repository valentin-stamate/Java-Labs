import loader.Loader;
import util.Util;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class MainClass {

    private final static String PATH = "C:\\Users\\Valentin\\Desktop";

    public static void main(String... args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {

//        compulsory();
        optional();
    }

    private static void compulsory() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String file = "source.SourceClassOne";

        Loader loader = new Loader();
        loader.addPath(PATH);

        Class<?> clazz = loader.loadClass(file);

        System.out.println("Package: ");
        System.out.println(clazz.getPackage());

        Method[] methods = clazz.getMethods();

        System.out.println("\nMethods with output for @Test annotated function: ");
        for (Method method : methods) {
            System.out.printf("%10s -> ", method.getName());

            Class<?>[] parameters = method.getParameterTypes();

            StringBuilder stringBuilder = new StringBuilder();

            for (Class<?> parameter : parameters) {
                stringBuilder.append(parameter.getName()).append(" ");
            }
            System.out.printf("| %20s |", stringBuilder);

            Annotation[] annotations = method.getAnnotations();
            stringBuilder = new StringBuilder();

            String methodOutput = "";
            for (Annotation annotation : annotations) {
                methodOutput = "";
                stringBuilder.append(annotation.toString()).append(" ");

                if (annotation.toString().equals("@org.junit.jupiter.api.Test()") && Modifier.isStatic(method.getModifiers())) {
                    Object obj = clazz.getConstructor().newInstance();
                    methodOutput = (String) method.invoke(obj);
                }

            }

            System.out.printf("| %45s |", stringBuilder);
            System.out.printf("| %20s |", methodOutput);

            System.out.println("");
        }
    }

    private static void optional() {
        File folder = new File(PATH + "\\source");
        List<String> files = new ArrayList<>();
        Util.getFiles(folder, files);

        for (String file : files) {
            System.out.println(file);
        }

    }
}
