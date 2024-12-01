package com.truongdc.movie_tmdb.feature.setting

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.core.os.LocaleListCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.truongdc.movie_tmdb.core.designsystem.R.string
import com.truongdc.movie_tmdb.core.designsystem.extensions.getDisplayName
import com.truongdc.movie_tmdb.core.designsystem.theme.AppTheme
import com.truongdc.movie_tmdb.core.designsystem.theme.supportsDynamicTheming
import com.truongdc.movie_tmdb.core.model.DarkThemeConfig
import com.truongdc.movie_tmdb.core.model.Language
import com.truongdc.movie_tmdb.core.model.ThemeBrand
import com.truongdc.movie_tmdb.core.ui.UiStateContent


@Composable
fun SettingsDialog(
    onLogout: () -> Unit,
    onDismiss: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    UiStateContent(
        viewModel = viewModel,
        modifier = Modifier,
        onEventEffect = {},
        content = { uiState ->
            val configuration = LocalConfiguration.current
            AlertDialog(
                properties = DialogProperties(usePlatformDefaultWidth = false),
                modifier = Modifier.widthIn(max = configuration.screenWidthDp.dp - 80.dp),
                onDismissRequest = { },
                title = {
                    Text(
                        text = stringResource(id = string.settings),
                        style = MaterialTheme.typography.titleLarge,
                    )
                },
                text = {
                    HorizontalDivider()
                    Column(Modifier.verticalScroll(rememberScrollState())) {
                        SettingsPanel(
                            settings = uiState.userEditableSettings,
                            supportDynamicColor = supportsDynamicTheming(),
                            onChangeThemeBrand = viewModel::setThemeBrand,
                            onChangeDynamicColorPreference = viewModel::setDynamicColor,
                            onChangeDarkThemeConfig = viewModel::setDarkTheme,
                            onChangeLanguage = viewModel::setLanguage
                        )
                        HorizontalDivider(Modifier.padding(top = 8.dp))

                    }
                },
                confirmButton = {
                    Text(
                        text = stringResource(id = string.ok).uppercase(),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .clickable { onDismiss() },
                    )
                },
                dismissButton = {
                    Text(
                        text = stringResource(id = string.logout).uppercase(),
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .clickable {
                                onLogout()
                                onDismiss()
                            },
                    )
                }
            )
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ColumnScope.SettingsPanel(
    settings: UserEditableSettings,
    supportDynamicColor: Boolean,
    onChangeThemeBrand: (themeBrand: ThemeBrand) -> Unit,
    onChangeDynamicColorPreference: (useDynamicColor: Boolean) -> Unit,
    onChangeDarkThemeConfig: (darkThemeConfig: DarkThemeConfig) -> Unit,
    onChangeLanguage: (language: Language) -> Unit,
) {
    SettingsDialogSectionTitle(text = stringResource(id = string.languages))
    Column(Modifier.selectableGroup()) {
        SettingDialogSelectLanguage { language -> onChangeLanguage(language) }
    }
    SettingsDialogSectionTitle(text = "Theme")
    Column(Modifier.selectableGroup()) {
        SettingsDialogThemeChooserRow(
            text = stringResource(id = string.default_srt),
            selected = settings.brand == ThemeBrand.DEFAULT,
            onClick = { onChangeThemeBrand(ThemeBrand.DEFAULT) },
        )
        SettingsDialogThemeChooserRow(
            text = stringResource(id = string.android),
            selected = settings.brand == ThemeBrand.ANDROID,
            onClick = { onChangeThemeBrand(ThemeBrand.ANDROID) },
        )
    }
    AnimatedVisibility(visible = settings.brand == ThemeBrand.DEFAULT && supportDynamicColor) {
        Column {
            SettingsDialogSectionTitle(text = stringResource(id = string.use_dynamic_color))
            Column(Modifier.selectableGroup()) {
                SettingsDialogThemeChooserRow(
                    text = stringResource(id = string.yes),
                    selected = settings.useDynamicColor,
                    onClick = { onChangeDynamicColorPreference(true) },
                )
                SettingsDialogThemeChooserRow(
                    text = stringResource(id = string.no),
                    selected = !settings.useDynamicColor,
                    onClick = { onChangeDynamicColorPreference(false) },
                )
            }
        }
    }
    SettingsDialogSectionTitle(text = stringResource(id = string.dark_mode_preference))
    Column(Modifier.selectableGroup()) {
        SettingsDialogThemeChooserRow(
            text = stringResource(id = string.system_default),
            selected = settings.darkThemeConfig == DarkThemeConfig.FOLLOW_SYSTEM,
            onClick = { onChangeDarkThemeConfig(DarkThemeConfig.FOLLOW_SYSTEM) },
        )
        SettingsDialogThemeChooserRow(
            text = stringResource(id = string.light),
            selected = settings.darkThemeConfig == DarkThemeConfig.LIGHT,
            onClick = { onChangeDarkThemeConfig(DarkThemeConfig.LIGHT) },
        )
        SettingsDialogThemeChooserRow(
            text = stringResource(id = string.dark),
            selected = settings.darkThemeConfig == DarkThemeConfig.DARK,
            onClick = { onChangeDarkThemeConfig(DarkThemeConfig.DARK) },
        )
    }
}

@Composable
private fun SettingsDialogSectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
    )
}

@Composable
private fun SettingsDialogThemeChooserRow(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .selectable(
                selected = selected,
                role = Role.RadioButton,
                onClick = onClick,
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(
            selected = selected,
            onClick = null,
        )
        Spacer(Modifier.width(8.dp))
        Text(text)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingDialogSelectLanguage(
    onChangeLanguage: (Language) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            modifier = Modifier.menuAnchor(),
            value = stringResource(id = string.language),
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = AppTheme.colors.onPrimary,
                unfocusedIndicatorColor = AppTheme.colors.onPrimary,
                disabledIndicatorColor = AppTheme.colors.onPrimary,
            ),
        )

        ExposedDropdownMenu(expanded = expanded, onDismissRequest = {
            expanded = false
        }) {
            Language.entries.forEach { language ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = language.getDisplayName(LocalContext.current),
                        )
                    }, onClick = {
                        AppCompatDelegate.setApplicationLocales(
                            LocaleListCompat.forLanguageTags(language.code)
                        )
                        onChangeLanguage(language)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}
