package com.lsy.frescodemo;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.BasePostprocessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;

/**
 * Created by liusiyu.taloner on 2016/7/21.
 */
public class ShowFragment extends Fragment implements View.OnClickListener {

    private static Uri mUriNet;
    private static Uri mUriLocal;
    SimpleDraweeView commonImage;
    SimpleDraweeView scaleImage;
    SimpleDraweeView progressImage;
    SimpleDraweeView circleImage;
    SimpleDraweeView cornerImage;
    SimpleDraweeView gifImage;
    SimpleDraweeView paintImage;
    SimpleDraweeView moreImage;
    SimpleDraweeView listenerImage;
    SimpleDraweeView lowHighImage;
    SimpleDraweeView multiImage;
    GenericDraweeHierarchy multiHierarchy;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.showfrg1, container, false);
        mUriNet = Uri.parse("http://img3.duitang.com/uploads/item/201511/05/20151105203425_Lsxtk.jpeg");
        mUriLocal = Uri.parse(getResUrl(getActivity(), R.drawable.image_local));
        Button btnReset = (Button)view.findViewById(R.id.btn_reset);
        btnReset.setOnClickListener(this);
        commonImage = (SimpleDraweeView)view.findViewById(R.id.img_view);
        commonImage.setOnClickListener(this);
        circleImage = (SimpleDraweeView)view.findViewById(R.id.img_circle);
        circleImage.setOnClickListener(this);
        cornerImage = (SimpleDraweeView)view.findViewById(R.id.img_corner);
        cornerImage.setOnClickListener(this);
        scaleImage = (SimpleDraweeView)view.findViewById(R.id.img_scale);
        scaleImage.setOnClickListener(this);
        progressImage = (SimpleDraweeView)view.findViewById(R.id.img_progress);
        progressImage.setOnClickListener(this);
        gifImage = (SimpleDraweeView)view.findViewById(R.id.img_gif);
        gifImage.setOnClickListener(this);
        paintImage = (SimpleDraweeView)view.findViewById(R.id.img_paint);
        paintImage.setOnClickListener(this);
        moreImage = (SimpleDraweeView)view.findViewById(R.id.img_more);
        moreImage.setOnClickListener(this);
        listenerImage = (SimpleDraweeView)view.findViewById(R.id.img_listener);
        listenerImage.setOnClickListener(this);
        lowHighImage = (SimpleDraweeView)view.findViewById(R.id.img_lowandhigh);
        lowHighImage.setOnClickListener(this);
        multiImage = (SimpleDraweeView)view.findViewById(R.id.img_temp1);
        multiImage.setOnClickListener(this);
        return view;
    }

    public void showImage(){
        commonImage.setImageURI(mUriNet);
    }

    //网格
    private void showPaintImage(){
        Postprocessor redMeshPostprocessor = new BasePostprocessor() {
            @Override
            public String getName() {
                return "redMeshPostprocessor";
            }

            @Override
            public void process(Bitmap bitmap) {
                for (int x = 0; x < bitmap.getWidth(); x+=2) {
                    for (int y = 0; y < bitmap.getHeight(); y+=2) {
                        bitmap.setPixel(x, y, Color.RED);
                    }
                }
            }
        };

        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(mUriNet)
                .setPostprocessor(redMeshPostprocessor)
                .build();

        PipelineDraweeController controller = (PipelineDraweeController)
                Fresco.newDraweeControllerBuilder()
                        .setImageRequest(request)
                        .setOldController(paintImage.getController())
                        // other setters as you need
                        .build();
        paintImage.setController(controller);
    }

    //圆角图片
    private void showCornerImage(){
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(mUriNet)
                .setAutoPlayAnimations(true)
                .build();
        GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(getResources());
        RoundingParams roundingParams = RoundingParams.fromCornersRadius(90f);
        roundingParams.setOverlayColor(Color.GREEN);
        builder.setRoundingParams(roundingParams);
        GenericDraweeHierarchy hierarchy = builder.build();
        cornerImage.setHierarchy(hierarchy);
        cornerImage.setController(controller);
    }

    //图片缩放
    private void showScaleImage(){
        GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(getResources());
        multiHierarchy = builder.setActualImageScaleType(ScalingUtils.ScaleType.CENTER).build();
        scaleImage.setHierarchy(multiHierarchy);
        scaleImage.setImageURI(mUriNet);
    }

    //进度条
    private void showProgressImage(){
        Uri uri = Uri.parse("http://img3.duitang.com/uploads/item/201511/21/20151121090622_t5Syz.jpeg");
        GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(getResources());
        GenericDraweeHierarchy hierarchy = builder.setProgressBarImage(new ProgressBarDrawable()).build();
        progressImage.setHierarchy(hierarchy);
        progressImage.setImageURI(uri);
    }

    private void showGif(){
        Uri uriLocal = Uri.parse(getResUrl(getActivity(), R.drawable.gif_test));
        DraweeController controllerLocal = Fresco.newDraweeControllerBuilder()
                .setUri(uriLocal)
                .setAutoPlayAnimations(true)
                .build();
        gifImage.setController(controllerLocal);
    }

    //圆圈图片
    private void showCircleImage(){
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(mUriNet)
                .setAutoPlayAnimations(true)
                .build();
        RoundingParams roundingParams = new RoundingParams();
        roundingParams.setRoundAsCircle(true);
        GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(getResources());
        GenericDraweeHierarchy hierarchy = builder.setRoundingParams(roundingParams).build();
        circleImage.setHierarchy(hierarchy);
        circleImage.setController(controller);
    }

    //多图显示
    private void showMoreImage(){
        ImageRequest request1 = ImageRequest.fromUri(mUriLocal);
        ImageRequest request2 = ImageRequest.fromUri(mUriNet);
        ImageRequest[] requests = { request1, request2};
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setFirstAvailableImageRequests(requests)
                .setOldController(moreImage.getController())
                .build();
        moreImage.setController(controller);
    }

    //双分辨率
    private void showLowAndHigh(){
        Uri uriLow = Uri.parse(getResUrl(getActivity(), R.drawable.img_low));
        Uri uriHigh = Uri.parse("http://img3.duitang.com/uploads/item/201512/26/20151226205939_fSdTW.jpeg");
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setLowResImageRequest(ImageRequest.fromUri(uriLow))
                .setImageRequest(ImageRequest.fromUri(uriHigh))
                .setOldController(lowHighImage.getController())
                .build();
        lowHighImage.setController(controller);
    }

    //下载监听
    private void showListener(){
        ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable anim) {
                Toast.makeText(getActivity(), "图片加载成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo) {
            }

            @Override
            public void onFailure(String id, Throwable throwable) {
                Toast.makeText(getActivity(), "图片加载失败", Toast.LENGTH_SHORT).show();
            }
        };

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setControllerListener(controllerListener)
                .setUri(mUriNet)
                .build();
        listenerImage.setController(controller);
    }

    private void showMultiHierarchy(){
        multiImage.setHierarchy(multiHierarchy);
        multiImage.setImageURI(mUriNet);
    }

    public static String getResUrl(Context context, int id) {
        return String.format("res://%s/%d", context.getPackageName(),id);
    }

    public interface showFrgInterface{
        void resetOnClick();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_reset:
                if(getActivity() instanceof showFrgInterface){
                    ((showFrgInterface) getActivity()).resetOnClick();
                }
            case R.id.img_view:
                showImage();
                break;
            case R.id.img_scale:
                showScaleImage();
                break;
            case R.id.img_progress:
                showProgressImage();
                break;
            case R.id.img_corner:
                showCornerImage();;
                break;
            case R.id.img_circle:
                showCircleImage();
                break;
            case R.id.img_gif:;
                showGif();
                break;
            case R.id.img_paint:
                showPaintImage();
                break;
            case R.id.img_more:
                showMoreImage();
                break;
            case R.id.img_listener:;
                showListener();
                break;
            case R.id.img_lowandhigh:
                showLowAndHigh();
                break;
            case R.id.img_temp1:
                showMultiHierarchy();
                break;
        }
    }
}
