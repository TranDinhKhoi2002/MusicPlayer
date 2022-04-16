package pj.tdk.musicplayer

import android.app.Application
import android.widget.Toast
import cat.ereza.customactivityoncrash.config.CaocConfig
import com.anjlab.android.iab.v3.BillingProcessor
import com.anjlab.android.iab.v3.PurchaseInfo
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import pj.tdk.musicplayer.Constants.PRO_VERSION_PRODUCT_ID

class App: Application() {
    lateinit var billingProcessor: BillingProcessor
    private val wallpaperAccentManager = WallpaperAccentManager(this)

    override fun onCreate() {
        super.onCreate()
        instance = this

//        startKoin {
//            androidContext(this@App)
//            modules(appModules)
//        }
        // default theme
//        if (!ThemeStore.isConfigured(this, 3)) {
//            ThemeStore.editTheme(this)
//                .accentColorRes(R.color.md_deep_purple_A200)
//                .coloredNavigationBar(true)
//                .commit()
//        }
//        wallpaperAccentManager.init()
//
//        if (VersionUtils.hasNougatMR())
//            DynamicShortcutManager(this).initDynamicShortcuts()

        // automatically restores purchases
        billingProcessor = BillingProcessor(
            this, BuildConfig.GOOGLE_PLAY_LICENSING_KEY,
            object : BillingProcessor.IBillingHandler {
                override fun onProductPurchased(productId: String, details: PurchaseInfo?) {}

                override fun onPurchaseHistoryRestored() {
                    Toast.makeText(
                        this@App,
                        R.string.restored_previous_purchase_please_restart,
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onBillingError(errorCode: Int, error: Throwable?) {}

                override fun onBillingInitialized() {}
            })

        // setting Error activity
        CaocConfig.Builder.create().errorActivity(ErrorActivity::class.java).apply()
    }

    override fun onTerminate() {
        super.onTerminate()
        billingProcessor.release()
        wallpaperAccentManager.release()
    }

    companion object {
        private var instance: App? = null

        fun getContext(): App {
            return instance!!
        }

        fun isProVersion(): Boolean {
            return BuildConfig.DEBUG || instance?.billingProcessor!!.isPurchased(
                PRO_VERSION_PRODUCT_ID
            )
        }
    }
}