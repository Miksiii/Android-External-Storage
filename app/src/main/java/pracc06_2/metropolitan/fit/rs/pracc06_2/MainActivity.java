package pracc06_2.metropolitan.fit.rs.pracc06_2;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private Button buttonSend, buttonRead;
    private EditText inputMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // init
        buttonSend = (Button) findViewById(R.id.buttonSentMessage);
        buttonRead = (Button) findViewById(R.id.buttonReadMessage);
        inputMessage = (EditText) findViewById(R.id.inputMessage);
    }

    public void writeToExternalMemory(View v) {
        String externalState = Environment.getExternalStorageState();

        if(Environment.MEDIA_MOUNTED.equals(externalState)) {
            File root = Environment.getExternalStorageDirectory();
            File dir  = new File(root.getAbsolutePath()+"/MyAppFile");

            if (!dir.exists()) {
                dir.mkdir();
            }

            File fileToStoreData = new File(dir, "users_diary.txt");

            try {

                FileOutputStream fileStream = new FileOutputStream(fileToStoreData);
                fileStream.write(inputMessage.getText().toString().getBytes());
                fileStream.close();
                Toast.makeText(MainActivity.this, "Message saved to External Storage.", Toast.LENGTH_SHORT).show();
                inputMessage.setText("");

            } catch (FileNotFoundException e) {
                Toast.makeText(MainActivity.this, "Write: file not found.", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } catch (IOException e) {
                Toast.makeText(MainActivity.this, "Write: IO Exception.", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

        } else {
            Toast.makeText(MainActivity.this, "SD card not Found at the moment.", Toast.LENGTH_SHORT).show();
        }
    }

    public void readFromExternalMemory(View v) {
        File root = Environment.getExternalStorageDirectory();
        File dir  = new File(root.getAbsolutePath()+"/MyAppFile");
        File fileToStoreData = new File(dir, "users_diary.txt");
        String messageToRead;

        try {
            FileInputStream fileStream = new FileInputStream(fileToStoreData);
            InputStreamReader streamReader = new InputStreamReader(fileStream);
            BufferedReader bufferedReader = new BufferedReader(streamReader);
            StringBuffer stringBuffer = new StringBuffer();

            while((messageToRead = bufferedReader.readLine()) != null) {
                stringBuffer.append(messageToRead + "\n");
            }

            Toast.makeText(MainActivity.this, "Message: " + stringBuffer.toString(), Toast.LENGTH_SHORT).show();

        } catch (FileNotFoundException e) {
            Toast.makeText(MainActivity.this, "Read: file not found.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(MainActivity.this, "Read: io exception", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }


    }
}
