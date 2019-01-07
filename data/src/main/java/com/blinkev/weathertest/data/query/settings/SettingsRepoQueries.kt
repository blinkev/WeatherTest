package com.blinkev.weathertest.data.query.settings

import com.blinkev.weathertest.domain.repo.settings.GetFirstRunQuery
import com.blinkev.weathertest.domain.repo.settings.SetFirstRunQuery

interface SettingsRepoQueries : GetFirstRunQuery, SetFirstRunQuery