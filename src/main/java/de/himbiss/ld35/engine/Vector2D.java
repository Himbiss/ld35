package de.himbiss.ld35.engine;

/**
 * Created by Vincent on 18.04.2016.
 */
public final class Vector2D {

    private final float x;
    private final float y;

    public Vector2D (float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vector2D vector2D = (Vector2D) o;

        if (Float.compare(vector2D.x, x) != 0) return false;
        return Float.compare(vector2D.y, y) == 0;
    }

    @Override
    public int hashCode() {
        int result = (x != +0.0f ? Float.floatToIntBits(x) : 0);
        result = 31 * result + (y != +0.0f ? Float.floatToIntBits(y) : 0);
        return result;
    }

    public Vector2D mult(float mult) {
        return new Vector2D(x * mult, y * mult);
    }

    public float max() {
        return Math.max(x, y);
    }

    public Vector2D abs() {
        return new Vector2D(Math.abs(x), Math.abs(y));
    }
}
