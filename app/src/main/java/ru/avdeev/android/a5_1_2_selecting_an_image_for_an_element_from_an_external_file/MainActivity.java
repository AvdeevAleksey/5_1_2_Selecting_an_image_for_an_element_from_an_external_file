package ru.avdeev.android.a5_1_2_selecting_an_image_for_an_element_from_an_external_file;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private TextView textViewResult;
    private View filenameLayout;
    private ImageView backgroundImg;
    private ImageView defaultBackgroundImg;
    private EditText editTextFileName;

    private static final int CLEAR_BACKROUND = 0;
    private static final int DEFAULT_BACKROUND = 1;
    private static final int LOADED_BACKROUND = 2;
    private static String fileName = null;
    private static int backgroundMode = DEFAULT_BACKROUND;
    public static final int REQUEST_CODE_PERMISSION_READ_STORAGE = 10;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int permissionStatus = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionStatus != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE_PERMISSION_READ_STORAGE);
        }

        Toolbar settingsToolbar = findViewById(R.id.settings_toolbar);
        setSupportActionBar(settingsToolbar);

        textViewResult = findViewById(R.id.result);

        defaultBackgroundImg = findViewById(R.id.defaultBackroundImg);
        backgroundImg = findViewById(R.id.backroundImg);
        defaultBackgroundImg.setVisibility(View.INVISIBLE);
        backgroundImg.setVisibility(View.INVISIBLE);
        editTextFileName = findViewById(R.id.editTextFileName);
        Button loadButton = findViewById(R.id.loadButton);
        filenameLayout = findViewById(R.id.filenameLayout);

        loadButton.setOnClickListener(onClickListener);


        loadImage(fileName);

        setBackgroundMode();

    }

    final Button.OnClickListener onClickListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.loadButton:
                    fileName = editTextFileName.getText().toString();
                    filenameLayout.setVisibility(View.INVISIBLE);
                    if (loadImage(fileName)) {
                        Toast.makeText(MainActivity.this, getString(R.string.file_loaded), Toast.LENGTH_LONG).show();
                        backgroundMode = LOADED_BACKROUND;
                        setBackgroundMode();
                    } else {
                        Toast.makeText(MainActivity.this, getString(R.string.load_error), Toast.LENGTH_LONG).show();
                    }
                    break;
            }
            }
        };


    public void anyButtonClicked(View view) {
        if (textViewResult.getText().toString().equals(getText(R.string.button_zero).toString())) {textViewResult.setText("");}
        switch (view.getId()) {
            case R.id.btnZero: {
                printMyText(R.string.button_zero);
                break;
            }
            case R.id.btnOne: {
                printMyText(R.string.button_one);
                break;
            }
            case R.id.btnTwo: {
                printMyText(R.string.button_two);
                break;
            }
            case R.id.btnThree: {
                printMyText(R.string.button_three);
                break;
            }
            case R.id.btnFore: {
                printMyText(R.string.button_for);
                break;
            }
            case R.id.btnFive: {
                printMyText(R.string.button_five);
                break;
            }
            case R.id.btnSix: {
                printMyText(R.string.button_six);
                break;
            }
            case R.id.btnSeven: {
                printMyText(R.string.button_seven);
                break;
            }
            case R.id.btnEight: {
                printMyText(R.string.button_eight);
                break;
            }
            case R.id.btnNine: {
                printMyText(R.string.button_nine);
                break;
            }
            case R.id.btnPoint: {
                printMyText(R.string.button_point);
                break;
            }
            case R.id.btnClear: {
                textViewResult.setText(getString(R.string.button_zero));
                break;
            }
            case R.id.btnPlusMinus: {
                printMyText(R.string.button_plus_minus);
                break;
            }
            case R.id.btnPercent: {
                printMyText(R.string.button_percent);
                break;
            }
            case R.id.btnDivision: {
                printMyText(R.string.button_division);
                break;
            }
            case R.id.btnMultiplication: {
                printMyText(R.string.button_multiplication);
                break;
            }
            case R.id.btnMinus: {
                printMyText(R.string.button_minus);
                break;
            }
            case R.id.btnPlus: {
                printMyText(R.string.button_plus);
                break;
            }
            case R.id.btnSubmit: {
                printMyText(R.string.button_submit);
                break;
            }
        }
    }

    public void printMyText(int id){
        textViewResult.setText(textViewResult.getText().toString() + getString(id));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_clear_background) {
            backgroundMode = CLEAR_BACKROUND;
            setBackgroundMode();
            return true;
        }

        if (id == R.id.action_default_background) {
            backgroundMode = DEFAULT_BACKROUND;
            setBackgroundMode();
            return true;
        }

        if (id == R.id.action_load_background) {
            filenameLayout.setVisibility(View.VISIBLE);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setBackgroundMode() {
        switch (backgroundMode) {
            case LOADED_BACKROUND:
                backgroundImg.setVisibility(View.VISIBLE);
                defaultBackgroundImg.setVisibility(View.INVISIBLE);
                break;
            case CLEAR_BACKROUND:
                backgroundImg.setVisibility(View.INVISIBLE);
                defaultBackgroundImg.setVisibility(View.INVISIBLE);
                break;
            default:
                backgroundImg.setVisibility(View.INVISIBLE);
                defaultBackgroundImg.setVisibility(View.VISIBLE);
                break;
        }
    }

    public boolean loadImage(String fileName) {

        if (fileName == null) return false;
        if (fileName.length() == 0) return false;

        if (isExternalStorageWritable()) {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    fileName);
            Bitmap b = BitmapFactory.decodeFile(file.getAbsolutePath());
            if (b == null) return false;
            backgroundImg.setImageBitmap(b);
        } else {
            Toast.makeText(this, "File Error", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }
}