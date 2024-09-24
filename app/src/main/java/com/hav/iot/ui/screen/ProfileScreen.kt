package com.hav.iot.ui.screen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hav.iot.R
import com.hav.iot.ui.component.TextHeader
import com.hav.iot.ui.theme.SecondColor


@Composable
fun ProfileScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                SecondColor
            )
    ) {
        Column {
            Box(modifier = Modifier.padding(start = 10.dp, top = 10.dp)) {
                TextHeader(text = "Profile")
            }

            ProfileContent(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(10.dp)
            ) {
                TopProfileLayout()
                MainProfileContent()
            }
        }

    }
}


@Composable
fun ProfileContent(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(modifier) {
        content()
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TopProfileLayout() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        shape = RoundedCornerShape(8),
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Row(
                modifier = Modifier.padding(vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.vu),
                        contentDescription = "avatar"
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = stringResource(id = R.string.full_name),
                        style = TextStyle(
                            color = Color.Black,
                            fontFamily = font,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )

                    )

                    Text(
                        text = stringResource(id = R.string.user_name),
                        style = TextStyle(
                            color = Color.Black,
                            fontFamily = font,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp
                        )
                    )
                }
            }

            Text(
                modifier = Modifier.padding(vertical = 5.dp),
                text = stringResource(id = R.string.description),
                style = TextStyle(
                    color = Color.Black,
                    fontFamily = font,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                )
            )

            FlowRow(modifier = Modifier.padding(vertical = 5.dp)) {
                imageTextList.forEach {
                    ImageTextContent(
                        modifier = Modifier.padding(vertical = 5.dp),
                        icon = {
                            Icon(
                                painter = painterResource(id = it.icon),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(20.dp)
                            )
                        },
                        text =
                        {
                            Text(
                                text = it.text,
                                style = TextStyle(
                                    color = Color.Black,
                                    fontFamily = font,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 14.sp
                                )
                            )
                        }

                    )
                }
            }

        }

    }
}

@Composable
fun ImageTextContent(
    icon: @Composable () -> Unit,
    text: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        icon()
        Spacer(modifier = Modifier.width(5.dp))
        text()
        Spacer(modifier = Modifier.width(10.dp))
    }
}

@Composable
fun MainProfileContent() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        shape = RoundedCornerShape(8),
    ) {
        Column(modifier = Modifier.padding(5.dp)) {
            Text(
                modifier = Modifier
                    .padding(10.dp),
                text = "Results",
                style = TextStyle(
                    color = Color.Black,
                    fontFamily = font,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            )
            PopularContentList()

            Divider(modifier = Modifier.padding(vertical = 15.dp))

            GitContentItem(
                modifier = Modifier.padding(vertical = 2.dp),
                icon =
                {
                    Icon(
                        painter = painterResource(R.drawable.ic_menu),
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .padding(6.dp)
                    )
                },
                text =
                {
                    Text(
                        text = "Repositories",
                        style = TextStyle(
                            color = Color.Black,
                            fontFamily = font,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    )
                },
                endItem =
                {
                    Text(
                        modifier = Modifier.padding(5.dp),
                        text = "31",
                        style = TextStyle(
                            color = Color.Black,
                            fontFamily = font,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp
                        )
                    )
                }

            )
            GitContentItem(
                modifier = Modifier.padding(vertical = 2.dp),
                icon =
                {
                    Icon(
                        painter = painterResource(R.drawable.ic_star),
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .padding(6.dp)
                    )
                },
                text =
                {
                    Text(
                        text = "Starred",
                        style = TextStyle(
                            color = Color.Black,
                            fontFamily = font,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    )
                },
                endItem =
                {
                    Text(
                        modifier = Modifier.padding(5.dp),
                        text = "10",
                        style = TextStyle(
                            color = Color.Black,
                            fontFamily = font,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp
                        )
                    )
                }

            )
        }
    }
}

@Composable
fun PopularContentList() {
    LazyRow {
        items(
            profilePopularList,
        ) { item ->
            Surface(
                modifier = Modifier
                    .width(250.dp)
                    .padding(5.dp),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, SecondColor)
            ) {
                val context = LocalContext.current
                Column(modifier = Modifier
                    .padding(5.dp)
                    .clickable {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.url))
                        context.startActivity(intent)
                    }) {
                    Row(
                        modifier = Modifier.padding(vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_launcher_background),
                            contentDescription = null,
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = item.name,
                            style = TextStyle(
                                color = Color.Black,
                                fontFamily = font,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        )
                    }

                    Text(
                        modifier = Modifier.padding(vertical = 5.dp),
                        text = item.description,
                        style = TextStyle(
                            color = Color.Black,
                            fontFamily = font,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp
                        )
                    )

                    Row(
                        modifier = Modifier.padding(vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        ImageTextContent(
                            modifier = Modifier.padding(vertical = 5.dp),
                            icon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_star),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .size(15.dp)
                                )
                            },
                            text = {
                                Text(
                                    text = item.star,
                                )
                            }
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        ImageTextContent(
                            modifier = Modifier.padding(vertical = 5.dp),
                            icon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_launcher_background),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .size(10.dp)
                                )
                            },
                            text = {
                                Text(
                                    text = item.language,
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun GitContentItem(
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit,
    text: @Composable () -> Unit,
    endItem: @Composable () -> Unit,
) {
    Row(
        modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        icon()
        Column(
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .weight(1f)
        ) {
            text()
        }
        endItem()
    }
}


val imageTextList = listOf(
    ImageTextList(R.drawable.ic_msv, "B21DCCN795"),
    ImageTextList(R.drawable.ic_mail, "hoanganhvu271103@gmail.com"),
    ImageTextList(R.drawable.ic_tym, "IOT - 06")
)


data class ImageTextList(
    val icon: Int,
    val text: String
)

data class FeatureList(
    val name: String,
    val listIcon: Int,
    val githubUrl: String,
)

val profilePopularList = listOf(
    ProfilePopularList("Report", "Link to Report ...", "1", "PDF", "https://drive.google.com/file/d/1wujw_GBDOsEv6wFJHcHqPo9Wm26df6Nt/view?usp=sharing"),
    ProfilePopularList("Github Project ", "Link to Git Project...", "1", "Github", "https://github.com/hoanganhvu271/smart_home_app.git"),
    ProfilePopularList("API Docs", "Link to API Docs...", "2", "Postman", "https://iot-server-siz9.onrender.com/api-docs")
)

data class ProfilePopularList(
    val name: String,
    val description: String,
    val star: String,
    val language: String,
    val url: String
)

private val font = FontFamily(
    Font(R.font.notosans)
)
