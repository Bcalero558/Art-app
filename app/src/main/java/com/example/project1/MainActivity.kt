package com.example.project1

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.project1.data.Datasource
import com.example.project1.ui.theme.Project1Theme

class MainActivity : ComponentActivity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            Project1Theme {
                NavHost(
                    navController = navController,
                    startDestination = Screen.Home.route + "/{id}"
                ) {
                    composable(
                        Screen.Home.route + "/{id}",
                        arguments = listOf(navArgument("id") {
                            type = NavType.IntType
                            defaultValue = 0
                        })

                    ) {
                        HomePage(navController = navController)
                    }

                    composable(
                        Screen.Artist.route + "/{id}",
                        arguments = listOf(navArgument("id") {
                            type = NavType.IntType
                            defaultValue = 0
                        })
                    ) {
                        ArtistPage(navController = navController)
                    }
                }
            }
        }
    }
}



    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
     fun HomePage(navController: NavController) {
        var current by remember {
            mutableIntStateOf(
                navController.currentBackStackEntry?.arguments?.getInt("id") ?: 0
            )
        }

        val art = Datasource.arts[current]

        Scaffold(topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(id = R.string.app_name)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        })
        {innerPadding->

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding)

            ) { Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            ){
                Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.spacer_extra_large)))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Surface( ) {
                        ArtWall(current, art.artworkImageId, art.descriptionId, navController)

                    }
                }
                Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.spacer_extra_large)))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    ArtDescriptor(art.titleId, art.artistId, art.yearId)
                }
                Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.spacer_extra_large)))
                Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.spacer_extra_large)))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    DisplayController(current) {
                        current = if(it !in 0 ..<Datasource.arts.size) 0 else it
                    }
                }
            }


        }

    }
}


//  section a
@Composable
fun ArtWall(artistID: Int, artImageId: Int, artDescriptionId: Int, navController: NavController){
//todo: 1. Add image of artwork
        Image(painter = painterResource(id = artImageId),
            contentDescription = stringResource(id = artDescriptionId),

            //todo: 2. add click listener
            modifier = Modifier
                .clickable { navController.navigate(Screen.Artist.route + "/$artistID") })

}

@Composable
fun ArtDescriptor(artTitleId: Int, artistId: Int, artYearId: Int) {

//todo: 1. add art title
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center


        ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,

        ) {
            Text(text = stringResource(id = artTitleId), fontSize = 30.sp, fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(stringResource(id = artistId)+" " + stringResource(id = artYearId), fontWeight = FontWeight.SemiBold)        }
    }
//todo: 2 add artist name and year of art


}
@Composable
fun DisplayController(current: Int, updateCurrent: (Int) -> Unit) {
val enabled = true
    // todo 1. add previous button
    if(current !=0) {
        Button(onClick = {
            updateCurrent(
                current.minus(1)
            )
        }, modifier = Modifier.size(150.dp, 60.dp))
        {
            Text(text = stringResource(id = R.string.previous))
        }
    }else(
       OutlinedButton(onClick = {
    }, modifier = Modifier.size(150.dp, 60.dp))
    {
        Text(text = stringResource(id = R.string.previous))
    })
//todo 2 add next

    if( current != Datasource.arts.size - 1)(
        Button(onClick = {
        updateCurrent(
                current.plus(1)
    )
                     },modifier = Modifier.size(150.dp,60.dp))
    {
        Text(text = stringResource(id = R.string.next))
    })else(
            OutlinedButton(onClick = {
            }, modifier = Modifier.size(150.dp, 60.dp))
            {
                Text(text = stringResource(id = R.string.next))
            })
// todo disabled if on last or first
//enabled = current !=0
// enabled = current != Datasource.arts.size - 1

}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
    fun ArtistPage(navController: NavController) {
        val id = navController.currentBackStackEntry?.arguments?.getInt("id") ?: 0
    val art = Datasource.arts[id]

    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = { Text(stringResource(id = R.string.app_name)) },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        )
    })
    { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.spacer_extra_large)))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Image(
                        painter = painterResource(id = art.artistImageId),
                        
                        contentDescription = stringResource( id = art.artistId),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                        )
                    Text(text = stringResource(id = art.artistId)+ "\n \n"+stringResource(id = art.artistInfoId), fontSize = 30.sp)

                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Text(text = stringResource(id = art.artistBioId), fontSize = 20.sp, fontStyle = FontStyle.Italic)
                }
            }
            Button(onClick = {navController.navigate(Screen.Home.route + "/$id") }) {
                Text(text = stringResource(R.string.back))
            }

        }
    }
//todo 1.artist profile
        //todo 3. optional make code below look like design

    }


