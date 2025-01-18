import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.io.File;


public class Move {
  public static void moveMavenRepo() {
    String userHome = System.getProperty("user.home");
    Path source = Path.of(userHome, ".m2");
    Path target = Path.of(System.getProperty("user.dir"), "maven-dep");

    if (Files.exists(target)) {
      System.out.println("Maven repository already exists at: " + target);

      // delete it the folder
      System.out.println("Deleting the existing Maven repository at: " + target);
      try {
          Files.walk(target)
              .map(Path::toFile)
              .sorted((o1, o2) -> -o1.compareTo(o2))
              .forEach(File::delete);
      } catch (IOException e) {
          System.err.println("Failed to delete target directory: " + target);
          e.printStackTrace();
          return;
      }

  }else{
      try {
          Files.createDirectories(target);
      } catch (IOException e) {
          System.err.println("Failed to create target directory: " + target);
          e.printStackTrace();
          return;
      }
  }


    try {
        Files.walk(source)
            .forEach(sourcePath -> {
                Path targetPath = target.resolve(source.relativize(sourcePath));
                try {
                    Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    System.err.println("Failed to copy: " + sourcePath + " to " + targetPath);
                    e.printStackTrace();
                }
            });
        System.out.println("Maven repository moved successfully.");
    } catch (IOException e) {
        System.err.println("Error during moving Maven repository.");
        e.printStackTrace();
    }
}

  public static void main(String[] args) {
    moveMavenRepo();
  }
}
