import java.util.ArrayList;

public class manageGroups {
    private final ArrayList<Group> groups = new ArrayList<>();
    public void addGroup(Group group){
        groups.add(group);
    }
    public void deleteGroup(Group group){
        group.deleteAllProducts();
        groups.remove(group);
    }
}
