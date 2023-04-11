import java.util.ArrayList;

public class Shop {

    Shop(){
        this.groups = new ArrayList<>();
    }

    private ArrayList<Group> groups;
    public void addGroup(Group group){
        groups.add(group);
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
            if(group.getName().equals(name)) selected=group;
            break;
        }return selected;
    }
}
