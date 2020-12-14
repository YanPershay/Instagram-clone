package pershay.bstu.woolfy.addphoto;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import pershay.bstu.woolfy.R;
import pershay.bstu.woolfy.utils.Permissions;

public class PhotoFragment extends Fragment {

    private static final int PHOTO_FRAGMENT_TAB = 1;
    private static final int CAMERA_REQUEST_CODE = 5;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo, container, false);

        Button btnLaunchCamera = view.findViewById(R.id.btnLaunchCamera);
        btnLaunchCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((AddPhotoActivity)getActivity()).getCurrentTabNumber()== PHOTO_FRAGMENT_TAB){
                    if(((AddPhotoActivity)getActivity()).checkPermissions(Permissions.CAMERA_PERMISSION[0])){
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, CAMERA_REQUEST_CODE);
                    }else{
                        Intent intent = new Intent(getActivity(), AddPhotoActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                }
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CAMERA_REQUEST_CODE){
            Bitmap bitmap;
            bitmap = (Bitmap) data.getExtras().get("data");

            Intent intent = new Intent(getActivity(), NextActivity.class);
            intent.putExtra("selected_bitmap", bitmap);
            startActivity(intent);
        }
    }
}

