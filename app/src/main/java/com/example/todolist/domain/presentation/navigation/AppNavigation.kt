package com.example.todolist.domain.presentation.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todolist.domain.presentation.MainViewModel
import com.example.todolist.domain.presentation.home_screen.HomeScreen
import com.example.todolist.domain.presentation.update_screen.UpdateScreeen

@Composable
fun AppNavigation(mainViewModel: MainViewModel){

    val navController:NavHostController = rememberNavController()   // uygulamanın gezinme grafiğini barındıran ve gezinme işlemlerini yöneten bir Composable'dir.

    NavHost(navController = navController, startDestination = Screen.HomeScreen.name ){
        composable(
            route=Screen.HomeScreen.name
        ){
            HomeScreen(mainViewModel = mainViewModel, onUpdate = {id:Int->
                navController.navigate(
                    route="${Screen.UpdateScreen.name}/$id"
                )
            })
        }
        composable(
            route="${Screen.UpdateScreen.name}/{id}",
            arguments = listOf(navArgument("id"){
                type = NavType.IntType
            }),
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(300)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(300),
                )
            }
        ) {
        navBackStackEntry:NavBackStackEntry ->
        navBackStackEntry.arguments?.getInt("id").let { id:Int?->
            UpdateScreeen(
                id = id!!,
                mainViewModel = mainViewModel,
                onBack = {navController.popBackStack() })
            }
        }
        }
}