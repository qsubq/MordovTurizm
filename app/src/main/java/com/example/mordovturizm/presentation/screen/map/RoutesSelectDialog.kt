package com.example.mordovturizm.presentation.screen.map

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.example.mordovturizm.R
import com.yandex.mapkit.RequestPoint
import com.yandex.mapkit.RequestPointType
import com.yandex.mapkit.directions.DirectionsFactory
import com.yandex.mapkit.directions.driving.DrivingOptions
import com.yandex.mapkit.directions.driving.DrivingRoute
import com.yandex.mapkit.directions.driving.DrivingSession
import com.yandex.mapkit.directions.driving.VehicleOptions
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.PolylineMapObject

class RoutesSelectDialog(private val fragment: MapFragment, private val context: Context) :
    DialogFragment() {

    private val routes =
        arrayOf("Городской маршрут", "Пригородный маршрут", "Республиканский маршрут")

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Выберите маршрут")
        builder.setItems(
            routes,
        ) { dialog, which ->
            when (which) {
                0 -> {
                    fragment.routesCollection.clear()
                    fragment.lastRoute = fragment.usersLocation

                    val drivingRouter =
                        DirectionsFactory.getInstance().createDrivingRouter()
                    val drivingOptions = DrivingOptions().apply {
                        routesCount = 1
                    }
                    val vehicleOptions = VehicleOptions()

                    val points = buildList {
                        add(
                            RequestPoint(
                                fragment.usersLocation!!,
                                RequestPointType.WAYPOINT,
                                null,
                            ),
                        )
                        add(
                            RequestPoint(
                                Point(54.185199, 45.179833),
                                RequestPointType.WAYPOINT,
                                null,
                            ),
                        )
                        add(
                            RequestPoint(
                                Point(54.181452, 45.181558),
                                RequestPointType.WAYPOINT,
                                null,
                            ),
                        )
                        add(
                            RequestPoint(
                                Point(54.180641, 45.192975),
                                RequestPointType.WAYPOINT,
                                null,
                            ),
                        )
                        add(
                            RequestPoint(
                                Point(54.177748, 45.179689),
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
                                    fragment.routesCollection.addCollection()
                                        .addPolyline(drivingRoute.geometry)
                                        .apply {
                                            if (index == 0) styleMainRoute() else styleAlternativeRoute()
                                        }
                                }
                                dialog.cancel()
                            }

                            override fun onDrivingRoutesError(p0: com.yandex.runtime.Error) {}
                        }

                    fragment.drivingSession = drivingRouter.requestRoutes(
                        points,
                        drivingOptions,
                        vehicleOptions,
                        drivingRouteListener,
                    )
                    fragment.lastRoute = Point(54.161519, 45.255273)
                }

                1 -> {
                    fragment.routesCollection.clear()
                    fragment.lastRoute = fragment.usersLocation

                    val drivingRouter =
                        DirectionsFactory.getInstance().createDrivingRouter()
                    val drivingOptions = DrivingOptions().apply {
                        routesCount = 1
                    }
                    val vehicleOptions = VehicleOptions()

                    val points = buildList {
                        add(
                            RequestPoint(
                                fragment.usersLocation!!,
                                RequestPointType.WAYPOINT,
                                null,
                            ),
                        )
                        add(
                            RequestPoint(
                                Point(54.185199, 45.179833),
                                RequestPointType.WAYPOINT,
                                null,
                            ),
                        )
                        add(
                            RequestPoint(
                                Point(54.181452, 45.181558),
                                RequestPointType.WAYPOINT,
                                null,
                            ),
                        )
                        add(
                            RequestPoint(
                                Point(54.180641, 45.192975),
                                RequestPointType.WAYPOINT,
                                null,
                            ),
                        )
                        add(
                            RequestPoint(
                                Point(54.177748, 45.179689),
                                RequestPointType.WAYPOINT,
                                null,
                            ),
                        )
                        add(
                            RequestPoint(
                                Point(54.161519, 45.255273),
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
                                    fragment.routesCollection.addCollection()
                                        .addPolyline(drivingRoute.geometry)
                                        .apply {
                                            if (index == 0) styleMainRoute() else styleAlternativeRoute()
                                        }
                                }
                                dialog.cancel()
                            }

                            override fun onDrivingRoutesError(p0: com.yandex.runtime.Error) {}
                        }

                    fragment.drivingSession = drivingRouter.requestRoutes(
                        points,
                        drivingOptions,
                        vehicleOptions,
                        drivingRouteListener,
                    )
                    fragment.lastRoute = Point(54.161519, 45.255273)
                }

                else -> {
                    fragment.routesCollection.clear()
                    fragment.lastRoute = fragment.usersLocation

                    val drivingRouter =
                        DirectionsFactory.getInstance().createDrivingRouter()
                    val drivingOptions = DrivingOptions().apply {
                        routesCount = 1
                    }
                    val vehicleOptions = VehicleOptions()

                    val points = buildList {
                        add(
                            RequestPoint(
                                fragment.usersLocation!!,
                                RequestPointType.WAYPOINT,
                                null,
                            ),
                        )
                        add(
                            RequestPoint(
                                Point(54.185199, 45.179833),
                                RequestPointType.WAYPOINT,
                                null,
                            ),
                        )
                        add(
                            RequestPoint(
                                Point(54.181452, 45.181558),
                                RequestPointType.WAYPOINT,
                                null,
                            ),
                        )
                        add(
                            RequestPoint(
                                Point(54.180641, 45.192975),
                                RequestPointType.WAYPOINT,
                                null,
                            ),
                        )
                        add(
                            RequestPoint(
                                Point(54.177748, 45.179689),
                                RequestPointType.WAYPOINT,
                                null,
                            ),
                        )
                        add(
                            RequestPoint(
                                Point(54.161519, 45.255273),
                                RequestPointType.WAYPOINT,
                                null,
                            ),
                        )
                        add(
                            RequestPoint(
                                Point(54.718822, 43.225090),
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
                                    fragment.routesCollection.addCollection()
                                        .addPolyline(drivingRoute.geometry)
                                        .apply {
                                            if (index == 0) styleMainRoute() else styleAlternativeRoute()
                                        }
                                }
                                dialog.cancel()
                            }

                            override fun onDrivingRoutesError(p0: com.yandex.runtime.Error) {}
                        }

                    fragment.drivingSession = drivingRouter.requestRoutes(
                        points,
                        drivingOptions,
                        vehicleOptions,
                        drivingRouteListener,
                    )
                    fragment.lastRoute = Point(54.161519, 45.255273)
                }
            }
        }
        builder.setPositiveButton("Отмена") { dialog, _ ->
            dialog.cancel()
        }
        return builder.create()
    }

    fun PolylineMapObject.styleMainRoute() {
        zIndex = 10f
        setStrokeColor(ContextCompat.getColor(context, R.color.main_red))
        strokeWidth = 3f
        outlineColor = ContextCompat.getColor(context, R.color.black)
        outlineWidth = 1f
    }

    fun PolylineMapObject.styleAlternativeRoute() {
        zIndex = 5f
        setStrokeColor(ContextCompat.getColor(context, R.color.main_blue))
        strokeWidth = 4f
        outlineColor = ContextCompat.getColor(context, R.color.black)
        outlineWidth = 2f
    }
}
