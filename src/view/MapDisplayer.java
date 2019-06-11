package view;

public class MapDisplayer {

    String path;
    ViewController vc;

    public MapDisplayer(ViewController vc) {
        this.vc = vc;
    }

    public void convertPathToLine() {
        path=vc.vm.path.getValueSafe();
        System.out.println("solution is: "+path);
    }
}

