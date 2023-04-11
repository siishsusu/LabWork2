import java.io.*;

public class Files {
    void clearTxtFile(File file) {
        try {
            FileWriter writer = new FileWriter(file, false);
            writer.write("");
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    void createTxtGroups(String newGroupName, File file){
        try {
            FileWriter writer = new FileWriter(file, true);
            writer.write(newGroupName + "\n");
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    void createTxtProducts(String newProductName, File file){
        try {
            FileWriter writer = new FileWriter(file, true);
            writer.write(newProductName + "\n");
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    void editTxtGroups(String newGroupName, String oldGroupName, File file){
        try {
            File groupsTemp = new File("groupsTemp.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            BufferedWriter writer = new BufferedWriter(new FileWriter(groupsTemp));
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.equals(oldGroupName)) {
                    writer.write(newGroupName + "\n");
                } else {
                    writer.write(currentLine + "\n");
                }
            }reader.close();writer.close();
            file.delete();
            groupsTemp.renameTo(file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    void editTxtProducts(String newProductName, String oldProductName, File file){
        try {
            File groupsTemp = new File("groupsTemp.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            BufferedWriter writer = new BufferedWriter(new FileWriter(groupsTemp));
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.equals(oldProductName)) {
                    writer.write(newProductName + "\n");
                } else {
                    writer.write(currentLine + "\n");
                }
            }reader.close();writer.close();
            file.delete();
            groupsTemp.renameTo(file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
