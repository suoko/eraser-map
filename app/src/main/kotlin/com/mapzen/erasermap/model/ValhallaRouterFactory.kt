package com.mapzen.erasermap.model

import android.content.Context
import com.mapzen.android.MapzenRouter

public class ValhallaRouterFactory : RouterFactory {
    override fun getRouter(context: Context): MapzenRouter {
        return MapzenRouter(context)
    }
}
