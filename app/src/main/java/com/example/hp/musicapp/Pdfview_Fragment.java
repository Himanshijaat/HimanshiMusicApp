package com.example.hp.musicapp;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

/**
 * Created by hp on 12/23/2017.
 */

public class Pdfview_Fragment extends Fragment implements TextToSpeech.OnInitListener, View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    PDFView pdfView;
    private static final String FILENAME = "sample.pdf";
    private static final String FILE = "bhole.pdf";


    String file = "श्रीगणेश पूजा अपने आपमें बहुत ही महत्वपूर्ण व कल्याणकारी है। चाहे वह किसी कार्य की सफलता के लिए हो या फिर चाहे किसी कामनापूर्ति स्त्री, पुत्र, पौत्र, धन, समृद्धि के लिए या फिर अचानक ही किसी संकट मे पड़े हुए दुखों के निवारण हेतु हो। >   > - अर्थात्\u200C जब कभी किसी व्यक्ति को किसी अनिष्ट की आशंका हो या उसे नाना प्रकार के शारीरिक या आर्थिक कष्ट उठाने पड़ रहे हो तो उसे श्रद्धा एवं विश्वासपूर्वक किसी योग्य व विद्वान ब्राह्मण के सहयोग से श्रीगणपति प्रभु व शिव परिवार का व्रत, आराधना व पूजन करना चाहिए।\n" +
            "\n" +
            "श्रीगणेश चतुर्थी को पत्थर चौथ और कलंक चौथ के नाम भी जाना जाता है। यह प्रति वर्ष भाद्रपद मास को शुक्ल चतुर्थी के रूप में मनाया जाता है। चतुर्थी तिथि को श्री गणपति भगवान की उत्पत्ति हुई थी इसलिए इन्हें यह तिथि अधिक प्रिय है। जो विघ्नों का नाश करने वाले और ऋद्धि-सिद्धि के दाता हैं। इसलिए इन्हें सिद्धि विनायक भगवान भी कहा जाता है।";
    String file1 = "चौकी पर लक्ष्मी व गणेश की मूर्तियां इस प्रकार रखें कि उनका मुख पूर्व या पश्चिम में रहे। लक्ष्मीजी, गणेशजी की दाहिनी ओर रहें। पूजनकर्ता मूर्तियों के सामने की तरफ बैठें। कलश को लक्ष्मीजी के पास चावलों पर रखें। नारियल को लाल वस्त्र में इस प्रकार लपेटें कि नारियल का अग्रभाग दिखाई देता रहे व इसे कलश पर रखें। यह कलश वरुण का प्रतीक है।\n" +
            "दो बड़े दीपक रखें। एक में घी भरें व दूसरे में तेल। एक दीपक चौकी के दाईं ओर रखें व दूसरा मूर्तियों के चरणों में। इसके अतिरिक्त एक दीपक गणेशजी के पास रखें।\n" +
            "\n" +
            "मूर्तियों वाली चौकी के सामने छोटी चौकी रखकर उस पर लाल वस्त्र बिछाएं। कलश की ओर एक मुट्ठी चावल से लाल वस्त्र पर नवग्रह की प्रतीक नौ ढेरियां बनाएं। गणेशजी की ओर चावल की सोलह ढेरियां बनाएं। ये सोलह मातृका की प्रतीक हैं। नवग्रह व षोडश मातृका के बीच स्वस्तिक का चिह्न बनाएं।\n" +
            "इसके बीच में सुपारी रखें व चारों कोनों पर चावल की ढेरी। सबसे ऊपर बीचोंबीच ॐ लिखें। छोटी चौकी के सामने तीन थाली व जल भरकर कलश रखें। थालियों की निम्नानुसार व्यवस्था करें- 1. ग्यारह दीपक, 2. खील, बताशे, मिठाई, वस्त्र, आभूषण, चन्दन का लेप, सिन्दूर, कुंकुम, सुपारी, पान, 3. फूल, दुर्वा, चावल, लौंग, इलायची, केसर-कपूर, हल्दी-चूने का लेप, सुगंधित पदार्थ, धूप, अगरबत्ती, एक दीपक।\n" +
            "इन थालियों के सामने यजमान बैठे। आपके परिवार के सदस्य आपकी बाईं ओर बैठें। कोई आगंतुक हो तो वह आपके या आपके परिवार के सदस्यों के पीछे बैठे।";
    String file2 = "सबसे पहले माता सरस्वती का ध्यान करें\n" +
            "या कुन्देन्दु तुषारहार धवला या शुभ्रवस्त्रावृता।\n" +
            "या वीणावरदण्डमण्डितकरा या श्वेतपद्मासना ।।\n" +
            "या ब्रह्माच्युतशंकरप्रभृतिभिर्देवैः सदा वन्दिता।\n" +
            "सा मां पातु सरस्वती भगवती निःशेषजाड्यापहा ।।1।।\n" +
            "शुक्लां ब्रह्मविचारसारपरमांद्यां जगद्व्यापनीं ।\n" +
            "वीणा-पुस्तक-धारिणीमभयदां जाड्यांधकारपहाम्।।\n" +
            "हस्ते स्फाटिक मालिकां विदधतीं पद्मासने संस्थिताम् ।\n" +
            "वन्दे तां परमेश्वरीं भगवतीं बुद्धिप्रदां शारदाम्।।2।।\n" +
            "\n" +
            "इसके बाद सरस्वती देवी की प्रतिष्ठा करें। हाथ में अक्षत लेकर बोलें “ॐ भूर्भुवः स्वः महासरस्वती, इहागच्छ इह तिष्ठ। इस मंत्र को बोलकर अक्षर छोड़ें। इसके बाद जल लेकर 'एतानि पाद्याद्याचमनीय-स्नानीयं, पुनराचमनीयम्।” प्रतिष्ठा के बाद स्नान कराएं: ॐ मन्दाकिन्या समानीतैः, हेमाम्भोरुह-वासितैः स्नानं कुरुष्व देवेशि, सलिलं च सुगन्धिभिः।। ॐ श्री सरस्वतयै नमः।। इदं रक्त चंदनम् लेपनम् से रक्त चंदन लगाएं। इदं सिन्दूराभरणं से सिन्दूर लगाएं। ‘ॐ मन्दार-पारिजाताद्यैः, अनेकैः कुसुमैः शुभैः। पूजयामि शिवे, भक्तया, सरस्वतयै नमो नमः।। ॐ सरस्वतयै नमः, पुष्पाणि समर्पयामि।’इस मंत्र से पुष्प चढ़ाएं फिर माला पहनाएं। अब सरस्वती देवी को इदं पीत वस्त्र समर्पयामि कहकर पीला वस्त्र पहनाएं।";
    String file3 = "माता की मूर्ति या तस्वीर के सामने कलश मिट्टी के ऊपर रखकर हाथ में अक्षत, फूल, और गंगाजल लेकर वरुण देव का आह्वान करें। कलश में सर्वऔषधी एवं पंचरत्न डालें। कलश के नीचे रखी मिट्टी में सप्तधान्य और सप्तमृतिका मिलाएं। आम के पत्ते कलश में डालें। कलश के ऊपर एक पात्र में अनाज भरकर इसके ऊपर एक दीप जलाएं। कलश में पंचपल्लव डालें और इसके ऊपर एक पानी वाला नारियल रखें जिस पर लाल रंग का वस्त्र लिपटा हो। अब कलश के नीचे मिट्टी में जौ के दानें फैला दें। इसके बाद देवी का ध्यान करें- खडगं चक्र गदेषु चाप परिघांछूलं भुशुण्डीं शिर:, शंखं सन्दधतीं करैस्त्रि नयनां सर्वांग भूषावृताम। नीलाश्म द्युतिमास्य पाद दशकां सेवे महाकालिकाम, यामस्तीत स्वपिते हरो कमलजो हन्तुं मधुं कैटभम॥ ";

    String file4 = "नवग्रह-पूजन के लिए सबसे पहले ग्रहों का आह्वान किया जाता है। उसके बाद उनकी स्थापना की जाती है। फिर बाएँ हाथ में अक्षत लेकर मंत्रोच्चारण करते हुए दाएँ हाथ से अक्षत अर्पित करते हुए ग्रहों का आह्वान किया जाता है। इस प्रकार सभी ग्रहों का आह्वान करके उनकी स्थापना की जाती है। इसके उपरांत हाथ में अक्षत लकेर मंत्र उच्चारित करते हुए नवग्रह मंडल में प्रतिष्ठा के लिये अर्पित करें। अब मंत्रोच्चारण करते हुए नवग्रहों की पूजा करें। ध्यान रहे पूजा विधि किसी विद्वान ब्राह्मण से ही संपन्न करवायें। पूजा नवग्रह मंदिर में भी की जा सकती है।";
    String file5 = "नवरात्र का पर्व शुरू होने में चंद दिन बाकी हैं और लोग अभी से इसकी तैयारियों में जुट गए हैं। पूजा संबंधित सामान की भी लोगों नें खरीददारी शुरू कर दी है। नवरात्र के 9 दिनों में देवी के अलग-अलग रूपों की पूजा होती है, लेकिन क्या आप जानते हैं उन 9 रूपों की पूजा विधि क्या है? कैसे उनकी पूजा करनी चाहिए? क्या सामान चाहिए?\n" +
            "नवरात्र पूजा के लिए ये सामान जरूरी\n" +
            "नवरात्र के पहले दिन घट स्थापना करें और उस दिन व्रत करने का संकल्प लें। घट स्थापना प्रतिपदा तिथि को की जाती है। घट यानि कलश..इसे भगवान गणेश का रूप माना जाता है और किसी भी तरह की पूजा में सबसे पहले पूजा जाता है।\n" +
            "घट स्थापना के लिए कुछ जरुरी सामान चाहिए जैसे कि पाट (जिस पर देवी मां को विराजमान किया जाएगा), जौं, साफ और शुद्ध मिट्टी, कलश, नारियल, आम के पत्ते, लाल कपड़ा या चुनरी, मिठाई, फूल, कपूर, धूप, अगरबत्ती, लौंग, देसी घी, कलावा, शुद्ध जल से भरा हुआ चांदी, सोने या फिर तांबे का लोटा, चावल, फूलों की माला, सुपारी इत्यादि। इस सामान के साथ आप देवी मां और उसके 9 रूपों की पूजा-अर्चना शुरू करें।\n" +
            "\n" +
            "ऐसे करें कलश स्थापना\n" +
            "\n" +
            "कलश स्थापना के लिए जरूरी है सबसे पहले पूजा स्थल को अच्छे से शुद्ध किया जाए और उसके बाद एक लकड़ी के पाटे पर लाल कपड़ा बिछाएं। उसके बाद हाथ में कुछ चावल लेकर भगवान गणेश का ध्यान करते हुए पाटे पर रख दें। अब जिस कलश को स्थापित करना है उसमें शुद्ध जल भरें, आम के पत्ते लगाएं और पानी वाला नारियल उस कलश पर रखें। इसके बाद उस कलश पर रोली से स्वास्तिक का निशान बनाएं। अब उस कलश को स्थापित कर दें। नारियल पर कलावा और चुनरी भी बांधें।\n" +
            "अब एक तरफ एक हिस्से में मिट्टी फैलाएं और उस मिट्टी में जौं डाल दें। इस तरह कलश की स्थापना हो गई और इसके बाद 9 दिनों की देवी मां की पूजा प्रारंभ होती है।\n";
    TextView text;
    TextToSpeech textToSpeech;
    Button speck;
    int pos;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pdfview, container, false);
        textToSpeech = new TextToSpeech(getActivity(), this);
        text = (TextView) view.findViewById(R.id.textview_pdf);
        speck = (Button) view.findViewById(R.id.speck);
        String position = getArguments().getString("id");
        pos = Integer.parseInt(position);
        text.setOnClickListener(this);
        text.setMovementMethod(new ScrollingMovementMethod());
        speck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(getActivity(), "item position" + pos, Toast.LENGTH_SHORT).show();
                int i = 0;
                if (i == TextToSpeech.SUCCESS) {
                    int result = textToSpeech.setLanguage(new Locale("hi"));
                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "This Language is not supported");
                    } else {
                        switch (pos) {
                            case 0:
                                String text1 = file.toString();
                                // String text="्योहारों की आरतियाँ".toString();
                                textToSpeech.speak(text1, TextToSpeech.QUEUE_FLUSH, null);
                                break;
                            case 1:
                                String text2 = file1.toString();
                                // String text="्योहारों की आरतियाँ".toString();
                                textToSpeech.speak(text2, TextToSpeech.QUEUE_FLUSH, null);
                                break;
                            case 2:
                                String text3 = file2.toString();
                                // String text="्योहारों की आरतियाँ".toString();
                                textToSpeech.speak(text3, TextToSpeech.QUEUE_FLUSH, null);
                                break;
                            case 3:
                                String text4 = file3.toString();
                                // String text="्योहारों की आरतियाँ".toString();
                                textToSpeech.speak(text4, TextToSpeech.QUEUE_FLUSH, null);
                                break;
                            case 4:
                                String text5 = file4.toString();
                                // String text="्योहारों की आरतियाँ".toString();
                                textToSpeech.speak(text5, TextToSpeech.QUEUE_FLUSH, null);
                                break;
                            case 5:
                                String text6 = file5.toString();
                                // String text="्योहारों की आरतियाँ".toString();
                                textToSpeech.speak(text6, TextToSpeech.QUEUE_FLUSH, null);
                                break;}}
                } else {Log.e("TTS", "Initilization Failed!");}
            }
        });
        switch (pos) {
            case 0:
                text.setText(file);
                break;
            case 1:
                text.setText(file1);
                break;
            case 2:
                text.setText(file2);
                break;
            case 3:
                text.setText(file3);
                break;
            case 4:
                text.setText(file4);
                break;
            case 5:
                text.setText(file5);
                break;
        }
        return view;
    }

    @Override
    public void onDestroy() {
        //    Don't forget to shutdown tts!
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onInit(int i) {

    }

    @Override
    public void onClick(View view) {

    }


}
