package sample;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.nio.CharBuffer;
import javafx.scene.image.Image;

import static com.sun.javafx.tools.resource.DeployResource.Type.icon;
import static java.lang.Thread.sleep;

public class Main extends Application {
//    static JFrame frame=new JFrame();

    @Override
    public void start(final Stage primaryStage) throws Exception{

        final Group root = new Group();
        primaryStage.setTitle("Hello World");
        final Scene scene = new Scene(root, 720, 450);
        primaryStage.setScene(scene);
        primaryStage.show();

        System.out.println("hello1");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket echoSocket = new Socket("127.0.0.1", 4242);
                    InputStream input = new BufferedInputStream(echoSocket.getInputStream());

                    byte[] buffer = new byte[1000000];
                    int ret;
                    while ((ret = input.read(buffer)) !=-1) {

                        BufferedImage img = ImageIO.read(new ByteArrayInputStream(buffer));
                        WritableImage image = new WritableImage(500, 500);

                        image = SwingFXUtils.toFXImage(img, null);
                        final ImageView imgv = new ImageView(image);
                        imgv.setFitHeight(450);
                        imgv.setFitWidth(720);

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                HBox box = new HBox();
                                box.getChildren().add(imgv);
                                root.getChildren().add(box);
                            }
                        });


                    }
                } catch (Exception e) {
                    System.out.println(e.fillInStackTrace());
                    System.out.println(e.toString());
                }
            }
        }).start();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
