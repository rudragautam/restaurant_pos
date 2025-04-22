package com.orderpos.ui.user

import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.orderpos.R
import com.orderpos.data.local.entities.MenuItem
import com.orderpos.databinding.FragmentDashboardBinding
import com.orderpos.databinding.FragmentNewOrderBinding
import com.orderpos.databinding.ItemCartBinding
import com.orderpos.databinding.ItemMenuBinding
import com.orderpos.ui.admin.adapter.CartAdapter
import com.orderpos.ui.admin.adapter.MenuAdapter
import com.orderpos.ui.admin.adapter.MenuItemAdapter
import com.orderpos.viewmodal.AddMenuViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale


class NewOrderFragment : Fragment() , PaymentDialog.PaymentListener{
    private val addRestaurantViewModel: AddMenuViewModel by viewModels()
    private var _binding: FragmentNewOrderBinding? = null
    private val menuAdapter by lazy { MenuAdapter() }
    private val cartAdapter by lazy { CartAdapter() }
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!



    fun getCurrentDateFormatted(): String {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val formatter = DateTimeFormatter.ofPattern("EEEE d MMM, yyyy", Locale.getDefault())
            return LocalDate.now().format(formatter)
        } else {
            // Fallback to SimpleDateFormat for older devices
            val dateFormat = SimpleDateFormat("EEEE d MMM, yyyy", Locale.getDefault())
            return dateFormat.format(Date())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewOrderBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.textView3.text=getCurrentDateFormatted()




        binding.rectangles.apply {
            layoutManager= GridLayoutManager(requireContext(), 4)
            adapter= menuAdapter

            menuAdapter.setOnMenuItemClickListener(object : MenuAdapter.OnMenuItemClickListener {
                override fun onMenuItemClicked(position: Int, item: MenuItem) {
                }
            })
        }


        binding.cartrectangles.apply {
            layoutManager= LinearLayoutManager(requireContext())
            adapter=cartAdapter
            cartAdapter.setOnCartItemClickListener(object : CartAdapter.OnCartItemClickListener {
                override fun onQuantityClicked(position: Int, item: MenuItem) {
                    showQuantityDialog(position)
                }
            })
        }

        binding.payment.setOnClickListener {
            showPaymentDialog()
        }
        observeRestaurants()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // In your activity or fragment where you want to show the dialog:
    private fun showPaymentDialog() {
        val dialog = PaymentDialog()
        dialog.show(childFragmentManager, "PaymentDialog")

        // Tablet sizing
        if (resources.configuration.smallestScreenWidthDp >= 600) {
            dialog.dialog?.window?.setLayout(
                (resources.displayMetrics.widthPixels * 0.8).toInt(),
                WindowManager.LayoutParams.WRAP_CONTENT
            )
        }
    }

    override fun onCashPaymentComplete() {
    }

    override fun onQRPaymentComplete() {
    }

    private fun showQuantityDialog(position: Int) {
        val dialog = QuantityDialog().apply {
            setInitialQuantity(1) // Optional: set initial quantity
            setMaxQuantity(10)    // Optional: set maximum quantity
            setMinQuantity(1)     // Optional: set minimum quantity
            setListener(object : QuantityDialog.QuantityListener {
                override fun onQuantitySelected(quantity: Int) {
                    // Handle the selected quantity
                    cartAdapter.updateItem(position,quantity)
                    Toast.makeText(requireContext(), "Selected quantity: $quantity", Toast.LENGTH_SHORT).show()
                }
            })
        }

        dialog.show(childFragmentManager, "QuantityDialog")
    }

    private fun observeRestaurants() {
        addRestaurantViewModel.allMenuList.observe(viewLifecycleOwner) { restaurants ->
            Log.d("RestaurantListFragment", "Observing restaurants${restaurants}")
            menuAdapter.submitList(restaurants)
            cartAdapter.submitList(restaurants)
        }
    }
}


class PaymentDialog : DialogFragment() {

    interface PaymentListener {
        fun onCashPaymentComplete()
        fun onQRPaymentComplete()
    }

    private var listener: PaymentListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? PaymentListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.dialog_payment, null)

        val ivCash = view.findViewById<ImageView>(R.id.iv_cash)
        val ivQr = view.findViewById<ImageView>(R.id.iv_qr)
        val ivQrCode = view.findViewById<ImageView>(R.id.iv_qr_code)
        val btnCashDone = view.findViewById<Button>(R.id.btn_cash_done)
        val btnQrDone = view.findViewById<Button>(R.id.btn_qr_done)

        // Cash payment click
        ivCash.setOnClickListener {
            btnCashDone.visibility = Button.VISIBLE
            btnQrDone.visibility = Button.GONE
            ivQrCode.visibility = ImageView.GONE
        }

        // QR payment click
        ivQr.setOnClickListener {
            btnQrDone.visibility = Button.VISIBLE
            btnCashDone.visibility = Button.GONE
            ivQrCode.visibility = ImageView.VISIBLE

            // Generate QR code
            val qrCode = generateQRCode("YourPaymentDataHere") // Replace with actual payment data
            ivQrCode.setImageBitmap(qrCode)
        }

        // Cash done button
        btnCashDone.setOnClickListener {
            listener?.onCashPaymentComplete()
            dismiss()
        }

        // QR done button
        btnQrDone.setOnClickListener {
            listener?.onQRPaymentComplete()
            dismiss()
        }

        builder.setView(view)
            .setTitle("Select Payment Method")
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }

        return builder.create()
    }

    private fun generateQRCode(content: String): Bitmap {
        val width = 500
        val height = 500
        val bitMatrix: BitMatrix = try {
            MultiFormatWriter().encode(
                content,
                BarcodeFormat.QR_CODE,
                width,
                height,
                null
            )
        } catch (e: Exception) {
            throw RuntimeException("Error generating QR code", e)
        }

        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        for (x in 0 until width) {
            for (y in 0 until height) {
                bitmap.setPixel(x, y, if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE)
            }
        }
        return bitmap
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

}

class QuantityDialog : DialogFragment() {

    interface QuantityListener {
        fun onQuantitySelected(quantity: Int)
    }

    private var listener: QuantityListener? = null
    private var currentQuantity = 1
    private var maxQuantity = 99
    private var minQuantity = 1

    fun setListener(listener: QuantityListener) {
        this.listener = listener
    }

    fun setMaxQuantity(max: Int) {
        this.maxQuantity = max
    }

    fun setMinQuantity(min: Int) {
        this.minQuantity = min
    }

    fun setInitialQuantity(quantity: Int) {
        this.currentQuantity = quantity.coerceIn(minQuantity, maxQuantity)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = LayoutInflater.from(requireContext())
        val view = inflater.inflate(R.layout.dialog_quantity, null)

        val tvQuantity = view.findViewById<TextView>(R.id.tv_quantity)
        val btnDecrease = view.findViewById<ImageButton>(R.id.btn_decrease)
        val btnIncrease = view.findViewById<ImageButton>(R.id.btn_increase)
        val btnConfirm = view.findViewById<MaterialButton>(R.id.btn_confirm)

        tvQuantity.text = currentQuantity.toString()

        btnDecrease.setOnClickListener {
            if (currentQuantity > minQuantity) {
                currentQuantity--
                tvQuantity.text = currentQuantity.toString()
            }
        }

        btnIncrease.setOnClickListener {
            if (currentQuantity < maxQuantity) {
                currentQuantity++
                tvQuantity.text = currentQuantity.toString()
            }
        }

        btnConfirm.setOnClickListener {
            listener?.onQuantitySelected(currentQuantity)
            dismiss()
        }

        builder.setView(view)

        // For tablet sizing
        val dialog = builder.create()
        if (resources.configuration.smallestScreenWidthDp >= 600) {
            dialog.window?.setLayout(
                (resources.displayMetrics.widthPixels * 0.5).toInt(),
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }

        return dialog
    }


}
