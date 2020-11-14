package com.flaviu.coronaplannerapp.pdf

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.flaviu.coronaplannerapp.databinding.PdfFragmentBinding
import com.itextpdf.text.Document
import com.itextpdf.text.Image
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*

fun getImage(file: String, context: Context, alignment: Int = Image.ALIGN_LEFT): Image {
    val ims: InputStream = context.assets.open(file)
    val bmp = BitmapFactory.decodeStream(ims)
    val stream = ByteArrayOutputStream()
    bmp.compress(Bitmap.CompressFormat.PNG, 100, stream)
    val image = Image.getInstance(stream.toByteArray())
    image.alignment = alignment
    return image
}

class PdfFragment : Fragment() {
    private lateinit var binding: PdfFragmentBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = PdfFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.generate.setOnClickListener{
            val lastName = binding.lastName.text
            val firstName = binding.firstName.text
            val address = binding.address.text
            val addressNow = binding.addressNow.text
            val dateBirth = binding.dateBirth.text
            val placeBirth = binding.placeBirth.text
            val society = binding.societate.text
            val officeLocation = binding.officeLocation.text
            val workplace = binding.workplace.text
            val medical = binding.medical.isChecked
            val pills = binding.pills.isChecked
            val caretake = binding.caretake.isChecked
            val death = binding.death.isChecked
            val interes = binding.interes.isChecked
            val document = Document()
            try {
                val file = requireContext().getExternalFilesDir(null)!!.path + "/declaratie.pdf"
                PdfWriter.getInstance(document, FileOutputStream(file));
                document.open();
                document.add(getImage("guvern.png", requireContext(), Image.ALIGN_RIGHT))
                var paragraph = Paragraph("DECLARATIE PE PROPRIA RASPUNDERE\n")
                paragraph.alignment = Paragraph.ALIGN_CENTER
                document.add(paragraph)
                document.add(Paragraph("\n"))
                document.add(Paragraph("Subsemnatul/a: $lastName $firstName domiciliat/a in: $address nascut/a in data de: $dateBirth in localitatea $placeBirth declar pe proprie raspundere, cunoscand prevederile articolului 326 din Codul Penal privind falsul in declaratii, ca ma deplasez in afara locuintei, in intervalul orar 23.00 - 05.00, din urmatorul/urmatoarele motive: "))
                if (interes)
                    document.add(getImage("checked.png", requireContext()))
                else document.add(getImage("unchecked.png", requireContext()))
                document.add(Paragraph("In interes profesional. Mentionez ca imi desfasor activitatea profesionala la institutia/societatea/organizatia $society cu sediul in $officeLocation si cu punct/e de lucru la urmatoarele adrese: " +
                        " $workplace"))
                if (medical)
                    document.add(getImage("checked.png", requireContext()))
                else document.add(getImage("unchecked.png", requireContext()))
                document.add(Paragraph("Asistenta medicala care nu poate fi amanata si nici de la distanta"))
                if (pills)
                    document.add(getImage("checked.png", requireContext()))
                else document.add(getImage("unchecked.png", requireContext()))
                document.add(Paragraph("Achizitionarea de medicamente"))
                if (caretake)
                    document.add(getImage("checked.png", requireContext()))
                else document.add(getImage("unchecked.png", requireContext()))
                document.add(Paragraph("Ingrijirea/Insotirea copilului si/sau asistenta persoanelor varstnice, bolnave sau cu dizabilitati"))
                if (death)
                    document.add(getImage("checked.png", requireContext()))
                else document.add(getImage("unchecked.png", requireContext()))
                document.add(Paragraph("Deces al unui membru al familiei"))
                val sdf = SimpleDateFormat("yyyy.MM.dd", Locale.US)
                val currentDateandTime: String = sdf.format(Date())
                document.add(Paragraph("\n"))
                document.add(Paragraph("Data: $currentDateandTime"));
                paragraph = Paragraph("Semnatura.........")
                paragraph.alignment = Paragraph.ALIGN_RIGHT
                document.add(paragraph)
                document.add(Paragraph("\n"))
                document.add(Paragraph("*Declaratia pe propria raspundere poate fi scrisa de mana, cu conditia preluarii tuturor elementelor prezentate mai sus"))
                document.add(Paragraph("**Declaratia pe propria raspundere poate fi stocata si prezentata pentru control pe dispozitive electronice mobile, cu conditia ca pe documentul prezentat sa existe semnatura olografa a persoanei care foloseste Declaratia si data pentru care este valabila declaratia."))
                document.close();
            }catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }
}