package swingcomponents;

public interface PortionListener {
    // Abstract functions that should be realised
    void onClick(int x, int y);
    void onPress(int x, int y);
    void onDrag(int x, int y);
    void onRelease(int x, int y);
    void onMove(int x, int y);
}
