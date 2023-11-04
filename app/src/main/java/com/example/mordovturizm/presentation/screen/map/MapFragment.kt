package com.example.mordovturizm.presentation.screen.map

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.mordovturizm.R
import com.example.mordovturizm.databinding.BottomSheetDialogLayoutBinding
import com.example.mordovturizm.databinding.FragmentMapBinding
import com.example.mordovturizm.domain.model.PointModel
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.RequestPoint
import com.yandex.mapkit.RequestPointType
import com.yandex.mapkit.directions.DirectionsFactory
import com.yandex.mapkit.directions.driving.DrivingOptions
import com.yandex.mapkit.directions.driving.DrivingRoute
import com.yandex.mapkit.directions.driving.DrivingSession
import com.yandex.mapkit.directions.driving.VehicleOptions
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObject
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.map.PolylineMapObject
import com.yandex.mapkit.map.TextStyle
import com.yandex.runtime.image.ImageProvider

private const val MAPKIT_API_KEY = "0ed58fb9-cd9f-4f31-af82-85375d002250"
private const val ZOOM_VALUE: Float = 11.0f
val startLocation = Point(54.187435, 45.183934)

class MapFragment : Fragment() {
    private lateinit var binding: FragmentMapBinding

    private val email by lazy {
        arguments?.getString("email")
    }

    private val mapOfPoints: MutableMap<String, MapObjectTapListener> = mutableMapOf()
    lateinit var drivingSession: DrivingSession

    var usersLocation: Point? = null
    private lateinit var mapObjectCollection: MapObjectCollection
    lateinit var routesCollection: MapObjectCollection
    var lastRoute: Point? = null

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted ->
        if (isGranted) {
            getCurrentUserLocation()
        } else {
            Toast.makeText(
                requireContext(),
                "Для корректной работы приложения требуется данное разрешение",
                Toast.LENGTH_SHORT,
            ).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val haveApiKey = savedInstanceState?.getBoolean("haveApiKey") ?: false
        if (!haveApiKey) {
            MapKitFactory.setApiKey(MAPKIT_API_KEY)
        }
        MapKitFactory.initialize(requireContext())
        binding = FragmentMapBinding.inflate(layoutInflater, container, false)
        mapObjectCollection = binding.map.map.mapObjects
        routesCollection = mapObjectCollection.addCollection()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val marker = createBitmapFromVector(R.drawable.baseline_location_on_24_light_red)

        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        getCurrentUserLocation()

        binding.imgUserLocation.setOnClickListener {
            getCurrentUserLocation()
        }

        binding.imgSettings.setOnClickListener {
            it.performLongClick()
        }
        registerForContextMenu(binding.imgSettings)

        listOfPoints.forEach { point ->
            createMapObject(point, mapObjectCollection, marker)
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?,
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menu.add(Menu.NONE, 101, Menu.NONE, "Выбрать маршрут")
        menu.add(Menu.NONE, 102, Menu.NONE, "Очистить маршрут")
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            101 -> {
                RoutesSelectDialog(
                    this,
                    requireContext(),
                ).show(requireActivity().supportFragmentManager, "RoutesSelectDialog")
            }

            else -> {
                routesCollection.clear()
                lastRoute = usersLocation
            }
        }
        return true
    }

    private fun createMapObject(
        point: PointModel,
        mapObjectCollection: MapObjectCollection,
        marker: Bitmap?,
    ) {
        val mapObjectTapListener = object : MapObjectTapListener {
            override fun onMapObjectTap(p0: MapObject, p1: Point): Boolean {
                Log.i("Map Fragment", "Tab on the point")
                val dialog = BottomSheetDialog(requireContext())
                val dialogBinding = BottomSheetDialogLayoutBinding.inflate(dialog.layoutInflater)
                dialog.setContentView(dialogBinding.root)

                with(dialogBinding) {
                    imgDialog1.setImageResource(point.photoId.first())
                    tvAddress.text = point.address
                    tvName.text = point.name
                    tvDescription.text = point.description

                    btnMakeRoute.setOnClickListener {
                        val drivingRouter =
                            DirectionsFactory.getInstance().createDrivingRouter()
                        val drivingOptions = DrivingOptions().apply {
                            routesCount = 1
                        }
                        val vehicleOptions = VehicleOptions()

                        val startPoint = if (lastRoute == null) {
                            usersLocation ?: startLocation
                        } else {
                            lastRoute
                        }

                        val points = buildList {
                            add(
                                RequestPoint(
                                    startPoint!!,
                                    RequestPointType.WAYPOINT,
                                    null,
                                ),
                            )
                            add(
                                RequestPoint(
                                    point.coord,
                                    RequestPointType.WAYPOINT,
                                    null,
                                ),
                            )
                        }

                        val drivingRouteListener =
                            object : DrivingSession.DrivingRouteListener {
                                override fun onDrivingRoutes(drivingRoutes: MutableList<DrivingRoute>) {
                                    if (drivingRoutes.isEmpty()) return
                                    drivingRoutes.forEachIndexed { index, drivingRoute ->
                                        routesCollection.addCollection()
                                            .addPolyline(drivingRoute.geometry)
                                            .apply {
                                                if (index == 0) styleMainRoute() else styleAlternativeRoute()
                                            }
                                    }
                                    dialog.cancel()
                                }

                                override fun onDrivingRoutesError(p0: com.yandex.runtime.Error) {}
                            }

                        drivingSession = drivingRouter.requestRoutes(
                            points,
                            drivingOptions,
                            vehicleOptions,
                            drivingRouteListener,
                        )
                        lastRoute = point.coord
                    }
                }
                dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
                dialog.show()
                return true
            }
        }

        mapOfPoints[point.name] = mapObjectTapListener

        val mapObject = mapObjectCollection.addPlacemark(
            point.coord,
            ImageProvider.fromBitmap(marker),
        )
        mapObject.setText(point.name)
        mapObject.setTextStyle(TextStyle().setPlacement(TextStyle.Placement.BOTTOM))
        mapObject.addTapListener(mapObjectTapListener)
    }

    private fun getCurrentUserLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION,
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION,
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val personMarker = createBitmapFromVector(R.drawable.baseline_circle_24)
            LocationServices.getFusedLocationProviderClient(requireActivity()).lastLocation.addOnSuccessListener {
                val point = if (it != null) Point(it.latitude, it.longitude) else startLocation
                usersLocation = point
                binding.map.map.move(
                    CameraPosition(
                        point,
                        ZOOM_VALUE,
                        0.0f,
                        0.0f,
                    ),
                    Animation(Animation.Type.SMOOTH, 1.5f),
                    null,
                )

                mapObjectCollection.addPlacemark(
                    point,
                    ImageProvider.fromBitmap(personMarker),
                )
            }
        }
    }

    fun PolylineMapObject.styleMainRoute() {
        zIndex = 10f
        setStrokeColor(ContextCompat.getColor(requireContext(), R.color.main_red))
        strokeWidth = 3f
        outlineColor = ContextCompat.getColor(requireContext(), R.color.black)
        outlineWidth = 1f
    }

    fun PolylineMapObject.styleAlternativeRoute() {
        zIndex = 5f
        setStrokeColor(ContextCompat.getColor(requireContext(), R.color.main_blue))
        strokeWidth = 4f
        outlineColor = ContextCompat.getColor(requireContext(), R.color.black)
        outlineWidth = 2f
    }

    private fun createBitmapFromVector(art: Int): Bitmap? {
        val drawable = ContextCompat.getDrawable(requireContext(), art) ?: return null
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888,
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("haveApiKey", true)
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        binding.map.onStart()
    }

    override fun onStop() {
        super.onStop()
        MapKitFactory.getInstance().onStop()
        binding.map.onStop()
    }
}
