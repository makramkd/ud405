package com.udacity.gamedev.sierpinskitriangle;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * TODO: Start here
 *
 * Your challenge, should you choose to accept it, is to draw a Serpinski Triangle. I offer no hints
 * beyond the fact that ShapeRenderer has a very convenient triangle() function, and that using a
 * FitViewport can simplify matters considerably. Good luck!
 */


public class SierpinskiTriangle extends ApplicationAdapter {

    static final float SIZE = 10;
    private static final int RECURSIONS = 7;

    static final float WORLD_WIDTH = 20f;
    static final float WORLD_HEIGHT = 20f;

    static final float X1 = 10f;
    static final float Y1 = 10f;
    static final float X2 = X1 + SIZE;
    static final float Y2 = Y1;
    static final float X3 = 15f;
    static final float Y3 = Y1 + SIZE;

    private ShapeRenderer renderer;
    private FitViewport viewport;

    @Override
    public void create() {
        renderer = new ShapeRenderer();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT);
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        viewport.apply();

        renderer.setProjectionMatrix(viewport.getCamera().combined);

        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.BLACK);
        renderer.triangle(X1, Y1, X2, Y2, X3, Y3);

        sierpinski(X1, Y1, X2, Y2, X3, Y3, renderer, RECURSIONS);

        renderer.end();
    }

    private void sierpinski(float x1, float y1, float x2, float y2,
                            float x3, float y3, ShapeRenderer shapeRenderer, int recursions) {
        if (recursions == 0) {
            return;
        }

        Vector2 mid12 = midpoint(x1, y1, x2, y2);
        Vector2 mid13 = midpoint(x1, y1, x3, y3);
        Vector2 mid23 = midpoint(x2, y2, x3, y3);

        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.triangle(mid12.x, mid12.y, mid23.x, mid23.y, mid13.x, mid13.y);

        // decrement rec count
        recursions--;

        // recursive calls
        sierpinski(x1, y1, mid12.x, mid12.y, mid13.x, mid13.y, shapeRenderer, recursions);
        sierpinski(mid12.x, mid12.y, x2, y2, mid23.x, mid23.y, shapeRenderer, recursions);
        sierpinski(mid13.x, mid13.y, mid23.x, mid23.y, x3, y3, shapeRenderer, recursions);
    }

    private Vector2 midpoint(float x1, float y1, float x2, float y2) {
        return new Vector2((x1 + x2) / 2f, (y1 + y2) / 2f);
    }
}
