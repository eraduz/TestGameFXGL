import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.CollisionHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.concurrent.ThreadLocalRandom;

public class Game extends GameApplication {

    private Entity player;

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(800);
        gameSettings.setHeight(800);
        gameSettings.setTitle("Test project");
    }

    @Override
    protected void initGame() {

        // Creating player
        player = FXGL.entityBuilder()
                .at(400, 400)
                .viewWithBBox("koen_warner.png")
                .with(new CollidableComponent(true))
                .scale(2, 2)
                .type(EntityTypes.PLAYER)
                .buildAndAttach();

        FXGL.getGameTimer().runAtInterval(() -> {
            int randomPosition = ThreadLocalRandom.current().nextInt(80, FXGL.getGameScene().getAppWidth() - 80);

            FXGL.entityBuilder().at(randomPosition, randomPosition)
                    .viewWithBBox(new Rectangle(10, 10, Color.BLUE))
                    .with(new CollidableComponent(true))
                    .type(EntityTypes.BLOCK)
                    .buildAndAttach();
        }, Duration.millis(2000));


        FXGL.getGameScene().setBackgroundColor(Color.ORANGE);
    }

    @Override
    protected void initInput() {
        FXGL.onKey(KeyCode.W, () -> {
            player.translateY(-3);
        });
        FXGL.onKey(KeyCode.A, () -> {
            player.translateX(-3);
        });
        FXGL.onKey(KeyCode.S, () -> {
            player.translateY(3);
        });
        FXGL.onKey(KeyCode.D, () -> {
            player.translateX(3);
        });
    }

    @Override
    protected void initPhysics() {
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.PLAYER, EntityTypes.BLOCK) {
            @Override
            protected void onCollision(Entity player, Entity block) {
                block.removeFromWorld();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
