package Movables.Collidables;

// wall and ground limitations, their start is 0 and width/length are taken from panel's props
public class Boundary implements Collidable {
    public Integer width, height;

    public Boundary(Integer width, Integer height){
        this.width = width;
        this.height = height;
    }
}
