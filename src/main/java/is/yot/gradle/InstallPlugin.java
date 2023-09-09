package is.yot.gradle;

import is.yot.gradle.shadows.KotlinMultiplatformExtensionS;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.logging.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Map;


public class InstallPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        project.task("install").dependsOn(":minimize").doLast(task -> install(task, project));
        project.task("minimize").dependsOn(":nativeBinaries").doLast(task -> minimize(task, project));
    }

    private void install(Task task, Project project) {
        Logger logger = (Logger) project.getProperties().get("logger");

        @SuppressWarnings("unchecked")
        File installDir = new File(((Map<String, Object>) project.getProperties())
                .getOrDefault("installdir", System.getProperty("user.home") + "/Works/System/bin/arch")
                .toString())
                .getAbsoluteFile();

        KotlinMultiplatformExtensionS kotlin = new KotlinMultiplatformExtensionS(project.getProperties().get("kotlin"));
        kotlin.getNativeTargets().forEach(t -> {
            TargetType type = TargetType.byName(t.getKonanTarget().getName());
            t.getBinaries().forEach(b -> {
                if (b.getBuildType().isDebuggable())
                    return;
                File source = b.getOutputFile();
                if (!source.isFile()) {
                    logger.warn("Unable to find file " + source.getAbsoluteFile() + ", skipping");
                    return;
                }
                try {
                    File all = type.getAllFile(b.getBaseName(), installDir);
                    File link = type.getLinkFile(b.getBaseName(), installDir);

                    all.getParentFile().mkdirs();
                    all.delete();
                    Files.copy(source.toPath(), all.toPath(), StandardCopyOption.COPY_ATTRIBUTES);

                    link.getParentFile().mkdirs();
                    link.delete();
                    Files.createSymbolicLink(link.toPath(), all.toPath());
                } catch (IOException e) {
                    throw new InstallException("Unable to install " + source);
                }
            });
        });
    }


    private void minimize(Task task, Project project) {
        KotlinMultiplatformExtensionS kotlin = new KotlinMultiplatformExtensionS(project.getProperties().get("kotlin"));
        kotlin.getNativeTargets().forEach(t -> t.getBinaries().forEach(b -> {
            File file = b.getOutputFile().getAbsoluteFile();
            if (file.isFile()) {
                String path = file.getAbsolutePath();
                try {
                    new ProcessBuilder("strip", path)
                            .inheritIO()
                            .redirectErrorStream(true)
                            .start()
                            .waitFor();
                    new ProcessBuilder("upx", "--best", path)
                            .inheritIO()
                            .redirectErrorStream(true)
                            .start()
                            .waitFor();
                } catch (Exception e) {
                    throw new InstallException("Unable to UPX file " + path);
                }
            }
        }));
    }

}
