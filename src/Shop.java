import java.util.ArrayList;

public class Shop {

    Shop(){
        this.groups = new ArrayList<>();
    }

    private ArrayList<Group> groups;
    public void addGroup(Group group){
        groups.add(group);
    }
    ArrayList wasDeleted = new ArrayList<>();
    public ArrayList wasDeletedGroup(String groupName){
        wasDeleted.add(groupName);
        return wasDeleted;
    }
    public boolean isUniqueProduct(String productName){
        boolean isntUnique = false;
        for (Group group : getGroups()) {
            for (Product prod : group.getProducts()) {
                if(productName.equalsIgnoreCase(prod.getName())){
                    isntUnique = true; break;
                }
            }
        }if(isntUnique==true)return false;
        return true;
    }
    public boolean isUniqueGroup(String groupName){
        boolean isntUnique = false;
        for (Group group : getGroups()) {
                if(groupName.equalsIgnoreCase(group.getName())){
                    isntUnique = true; break;
                }
        }if(isntUnique==true)return false;
        return true;
    }
    public void deleteGroup(Group group){
        groups.remove(group);
    }
    public void deleteAllGroups(){
        groups.clear();
    }
    public ArrayList<Group> getGroups() {
        return groups;
    }
    public Group getOneGroup(String name){
        Group selected = null;
        for(Group group: getGroups()){
            if(group.getName().equals(name)) {
                selected=group; break;
            }
        }return selected;
    }
}
