package view;

public class ColorfullMapDisplayer {

    String path;
    ViewController vc;

    public ColorfullMapDisplayer(ViewController vc) {
        this.vc = vc;
    }

    public void convertPathToLine() {
        path=vc.vm.path.getValueSafe();
        System.out.println("solution is: "+path);
    }
}

