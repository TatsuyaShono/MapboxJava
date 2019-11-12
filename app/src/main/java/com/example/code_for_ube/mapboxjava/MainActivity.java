package com.example.code_for_ube.mapboxjava;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.opencsv.exceptions.CsvException;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import timber.log.Timber;

import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.annotation.Symbol;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions;
import com.mapbox.mapboxsdk.plugins.building.BuildingPlugin;
import com.mapbox.mapboxsdk.style.layers.Layer;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonOptions;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.mapboxsdk.utils.ColorUtils;

import java.util.ArrayList;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;

public class MainActivity extends AppCompatActivity implements
    OnMapReadyCallback, PermissionsListener {

    private PermissionsManager permissionsManager;
    private MapboxMap mapboxMap;
    private MapView mapView;

    private static final String SOURCE_ID = "SOURCE_ID";
    private static final String ICON_ID = "ICON_ID";
    private static final String LAYER_ID = "LAYER_ID";

    private int count = 0;
    private List<Integer> ids = new ArrayList<>();
    private List<String> title = new ArrayList<>();
    private List<String> text = new ArrayList<>();
    private List<Double> lat = new ArrayList<>();
    private List<Double> lon = new ArrayList<>();

    // CSV関連
    private CsvUtil mCsvUtil = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Mapboxのアクセストークンを確認.アプリケーションオブジェクトまたはmapviewを含む同じアクティビティで呼び出す必要がある
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        // XMLのMapViewが含まれるため、アクセストークンの構成後に呼び出す必要がある
        setContentView(R.layout.activity_main);

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        /*//CSVファイル読み込み
        KankospotFileDao csvFileDao = new KankospotFileDao();
        AssetManager assetManager = this.getResources().getAssets();
        try {
            // CSVファイルの読み込み
            InputStream inputStream = assetManager.open("kankospot.csv");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"UTF-8");
            BufferedReader bufferReader = new BufferedReader(inputStreamReader);
            Reader reader = bufferReader;
            List<KankospotEntity> items = csvFileDao.read(reader);
            // csvファイルのデータ数を保持
            count = items.size();
            // 取り込んだ値をマッピング
            for (KankospotEntity obj : items) {
                ids.add(obj.getId());
                title.add(obj.getTitle());
                text.add(obj.getText());
                lat.add(obj.getLatitude());
                lon.add(obj.getLatitude());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvException e) {
            e.printStackTrace();
        }*/

        readSample("kankospot.csv");
    }


    /**
     *  readSample
     */
    private void readSample( String fileName) {

        List<KankospotEntity> list = mCsvUtil.readSample(fileName);
        if (( list == null )||( list.size() == 0 )) {
            toast_long("can not read");
            return;
        }
        for (KankospotEntity obj : list) {
            ids.add(obj.getId());
            title.add(obj.getTitle());
            text.add(obj.getText());
            lat.add(obj.getLatitude());
            lon.add(obj.getLatitude());
        }

    } // readSample

    /**
     * toast_long
     */
    private void toast_long( String msg ) {
        ToastMaster.makeText( this, msg, Toast.LENGTH_LONG ).show();
    } // toast_long

    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        MainActivity.this.mapboxMap = mapboxMap;

        List<Feature> symbolLayerIconFeatureList = new ArrayList<>();
        symbolLayerIconFeatureList.add(Feature.fromGeometry(
                Point.fromLngLat(33.954833, 131.244487)));
        symbolLayerIconFeatureList.add(Feature.fromGeometry(
                Point.fromLngLat(33.954833, 131.257232)));
        symbolLayerIconFeatureList.add(Feature.fromGeometry(
                Point.fromLngLat(33.947918, 131.257232)));

        mapboxMap.setStyle(new Style.Builder().fromUri("mapbox://styles/mapbox/cjf4m44iw0uza2spb3q0a7s41")

                // Add the SymbolLayer icon image to the map style
                .withImage(ICON_ID, BitmapFactory.decodeResource(
                        MainActivity.this.getResources(), R.drawable.mapbox_marker_icon_default))

                // Adding a GeoJson source for the SymbolLayer icons.
                .withSource(new GeoJsonSource(SOURCE_ID,
                        FeatureCollection.fromFeatures(symbolLayerIconFeatureList)))

                // 実際のSymbolLayerをマップスタイルに追加します。
                // アイコンの中央が座標点に固定されるのではなく、赤いマーカーアイコンの下部が座標に固定されるオフセットが追加されます。
                // これはオフセットが常に必要なわけではなく、SymbolLayerアイコンに使用する画像に依存します。
                .withLayer(new SymbolLayer(LAYER_ID, SOURCE_ID)
                        .withProperties(PropertyFactory.iconImage(ICON_ID),
                                iconAllowOverlap(true),
                                iconOffset(new Float[]{0f, -9f}))
                ), new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {

                // ここで、データを追加したり、他のマップを調整したりできる
                enableLocationComponent(style);

                // 3Dマップの表示
                BuildingPlugin buildingPlugin = new BuildingPlugin(mapView, mapboxMap, style);
                buildingPlugin.setVisibility(true);
                buildingPlugin.getLight();
                buildingPlugin.setColor(Color.parseColor("#0f4f6b"));
                buildingPlugin.setMinZoomLevel(10.0f);
                buildingPlugin.setOpacity(0.5f);

                // symbol manager を作成する
                GeoJsonOptions geoJsonOptions = new GeoJsonOptions().withTolerance(0.4f);
                SymbolManager symbolManager = new SymbolManager(mapView, mapboxMap, style, null, geoJsonOptions);
                symbolManager.addClickListener(symbol -> Toast.makeText(MainActivity.this,
                        String.format("Symbol clicked %s", symbol.getId()),
                        Toast.LENGTH_SHORT
                ).show());
                symbolManager.addLongClickListener(symbol ->
                        Toast.makeText(MainActivity.this,
                                String.format("Symbol long clicked %s", symbol.getId()),
                                Toast.LENGTH_SHORT
                        ).show());

                // 非データ駆動型プロパティを設定する
                symbolManager.setIconAllowOverlap(true);
                symbolManager.setTextAllowOverlap(true);

                // symbol を作成する
                SymbolOptions symbolOptions = new SymbolOptions()
                        .withLatLng(new LatLng(33.954833, 131.244487))
                        //.withIconImage(ID_ICON_AIRPORT)
                        .withIconSize(1.3f)
                        .withSymbolSortKey(10.0f)
                        .withDraggable(true);
                Symbol symbol = symbolManager.create(symbolOptions);
                Timber.e(symbol.toString());

                // 近くの symbols　を作成する
                SymbolOptions nearbyOptions = new SymbolOptions()
                        .withLatLng(new LatLng(33.947918, 131.257232))
                        //.withIconImage(MAKI_ICON_CIRCLE)
                        .withIconColor(ColorUtils.colorToRgbaString(Color.YELLOW))
                        .withIconSize(2.5f)
                        .withSymbolSortKey(5.0f)
                        .withDraggable(true);
                symbolManager.create(nearbyOptions);

                findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mapboxMap.getStyle(new Style.OnStyleLoaded() {
                            @Override
                            public void onStyleLoaded(@NonNull Style style) {

                                Layer waterLayer = style.getLayer("water");

                                if (waterLayer != null) {
                                    waterLayer.setProperties(PropertyFactory.fillColor(Color.parseColor("#004f6b")));
                                }

                            }
                        });
                    }
                });


                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(33.947918, 131.257232))
                        .title("Title")
                        .snippet("Text"));

                /*// マーカーの追加 ※この記述方法は将来サポートされなくなる
                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(lat.get(count), lon.get(count)))
                        .title(text.get(count))
                        .snippet(title.get(count)));*/


                mapboxMap.setOnMarkerClickListener(new MapboxMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(@NonNull Marker marker) {
                        Toast.makeText(getApplicationContext(), "マーカークリック: " + marker.getTitle(), Toast.LENGTH_LONG).show();
                        return false;
                    }
                });
            }
        });
    }

    @SuppressWarnings({"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
        // 許可が有効になっているかどうかを確認し、要求されていない場合
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            // コンポーネントのインスタンスを取得
            LocationComponent locationComponent = mapboxMap.getLocationComponent();
            // オプションでアクティベート
            locationComponent.activateLocationComponent(
                    LocationComponentActivationOptions.builder(this, loadedMapStyle).build());
            // コンポーネントを表示可能にする
            locationComponent.setLocationComponentEnabled(true);
            // コンポーネントのカメラモードを設定する
            locationComponent.setCameraMode(CameraMode.TRACKING);
            // コンポーネントのレンダリングモードを設定する
            locationComponent.setRenderMode(RenderMode.COMPASS);
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this, R.string.user_location_permission_explanation, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            mapboxMap.getStyle(new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {
                    enableLocationComponent(style);
                }
            });
        } else {
            Toast.makeText(this, R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    @SuppressWarnings({"MissingPermission"})
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

}