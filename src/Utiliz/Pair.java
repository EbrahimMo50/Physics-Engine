package Utiliz;

// more convinent pair with x, y attributes instead of getKey, getValue
public class Pair<T1, T2> {
    public T1 x;
    public T2 y;

    public Pair(T1 t1, T2 t2){
        this.x = t1;
        this.y = t2;
    }

    public Pair(){
        this.x = null;
        this.y = null;
    }
}
