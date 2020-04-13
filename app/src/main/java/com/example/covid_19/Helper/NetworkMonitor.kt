
import android.content.Context
import android.net.ConnectivityManager

class NetworkMonitor
constructor(context: Context)
{
    var mContext: Context = context
    val isConnected: Boolean
    get()
    {
        val connectivityManager = mContext!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}
