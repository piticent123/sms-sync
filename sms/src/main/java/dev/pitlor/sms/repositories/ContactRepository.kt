package dev.pitlor.sms.repositories

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ContactRepository @Inject constructor(@ApplicationContext private val context: Context)