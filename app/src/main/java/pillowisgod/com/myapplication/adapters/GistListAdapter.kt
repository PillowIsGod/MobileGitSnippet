package pillowisgod.com.myapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import pillowisgod.com.myapplication.R
import pillowisgod.com.myapplication.data.repositories.model.getmodels.GistResponseModel
import pillowisgod.com.myapplication.routers.GistListRouter

class GistListAdapter(private val fragment: Fragment) : RecyclerView.Adapter<GistListAdapter.GistListViewHolder>() {

    private val data : MutableList<GistResponseModel> = ArrayList()
    private val router = GistListRouter(fragment)

    fun setData(modelGists : List<GistResponseModel>) {
        data.clear()
        data.addAll(modelGists)
        notifyDataSetChanged()
    }
    fun addData(modelGists: List<GistResponseModel>) {
        data.addAll(modelGists)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GistListViewHolder {
        return GistListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cell_gist_item, parent, false))

    }

    override fun onBindViewHolder(holder: GistListViewHolder, position: Int) {
        holder.bind(modelGist = data[position])
        holder.itemView.setOnClickListener {
            router.routeToGistFragm(data[position])
        }
    }

    override fun getItemCount(): Int = data.size

    class GistListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvFileName = itemView.findViewById<TextView>(R.id.tvFileName)
        private val tvDate = itemView.findViewById<TextView>(R.id.tvDate)

        fun bind(modelGist: GistResponseModel) {
            var string : String = modelGist.description
            if(modelGist.description.count()>40) {
                string = "${modelGist.description.substring(0, 40)}..."
            }
            tvDate.text = modelGist.date.substringBeforeLast("T")
            tvFileName.text = string
        }

    }
}