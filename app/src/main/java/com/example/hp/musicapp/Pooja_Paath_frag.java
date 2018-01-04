package com.example.hp.musicapp;

import android.app.Fragment;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by hp on 12/20/2017.
 */

public class Pooja_Paath_frag extends Fragment implements TextToSpeech.OnInitListener {
    SearchView searchView;



//
//    public Pooja_Paath_frag() {
//        // Required empty public constructor
//
//    }
//
//
//    String[] questions = {
//            "गणपति पूजा",
//            "महा लक्ष्मी पूजा",
//            "सरस्वती पूजा",
//            "दुर्गा पूजा",
//            "काली पूजा",
//            "नव ग्रह पूजा",
//            "गयात्री पूजा",
//            "होली पूजा",
//            "दीपावली पूजा",
//            "नारात्रे पूजा"
//
//
//    };
//    String[] answers = {
//
//            "किसी भी उद्यम शुरू होने से पहले गणपति पूजा की जाती है। गणेश को शिव और पार्वती के पुत्र के रूप में सम्मानित किया गया है, और हमेशा सबसे अधिक पूजा और अनुष्ठानों में सम्मानित किया जाता है। गणेश को विगेश्वर, विनायक, गजमुखा और ऐंचन के रूप में भी जाना जाता है। गणपति 'मूलधारा चक्र' की अध्यक्षता वाली देवता है जो मानव शरीर में सात प्रमुख ऊर्जा केंद्रों में से एक है। वह सिद्धि (उपक्रमों में सफलता), और बुद्ध (बुद्धि) के लिए पूजा की जाती है। वह शिक्षा, ज्ञान और ज्ञान, साहित्य और ललित कला के देवता भी हैं। ज्ञान और ज्ञान को बेहतर बनाने के लिए बाधाएं दूर करने के लिए गणपति पूजा की जाती है।",
//            "देवी लक्ष्मी को महा लक्ष्मी पूजा की जाती है। महा लक्ष्मी परिवार में धन, भाग्य, शुद्धता, ईश्वरत्व, मित्रों, विवाह, बच्चों, भोजन, सौंदर्य, स्वास्थ्य और खुशी का उपहार देने वाला है। जब किसी स्थान पर प्रदर्शन किया जाता है तो लक्ष्मी पूजा समृद्धि और जबरदस्त सकारात्मक ऊर्जा प्रदान करती है और ज्ञान के साथ भाग्य को आकर्षित करती है।",
//            "अध्ययन में ज्ञान और निपुणता प्राप्त करने के लिए, बुद्धि को तेज करने और स्मृति को बेहतर बनाने के लिए देवी सरस्वती ज्ञान की देवी है, माता सरस्वती की पूजा हर छात्र द्वारा आयोजित की जाएगी चाहे स्कूल में, परीक्षा की तैयारी के कॉलेज।",
//                    "संस्कृत में, दुर्गा का अर्थ है 'वह जो समझ से बाहर है या मुश्किल है' दुर्गा भगवान माँ का प्रतिनिधित्व करते हैं और वह भगवान का ऊर्जा पहलू है। दुर्गा पूजा भारत के विभिन्न हिस्सों में विभिन्न शैलियों में मनाया जाने वाला सबसे बड़ा हिंदू त्यौहार है जिसमें भगवान को माँ के रूप में पसंद किया गया है। । गर्मियों की शुरुआत और सर्दियों की शुरुआत जलवायु और सौर प्रभाव के दो बहुत महत्वपूर्ण जंक्शन हैं। इन दोनों कालों को दिव्य मां की पूजा के लिए पवित्र अवसरों के रूप में लिया जाता है वे क्रमशः राम नवरात्रि (अप्रैल-मई) और दुर्गा नवरात्रि (सितंबर-अक्टूबर) द्वारा दर्शाए गए हैं। प्रकृति के परिवर्तनों के कारण लोगों के शरीर और मन में काफी बदलाव आते हैं। माता के रूप में दैवीय पूजा करते हुए हमारे जीवन में भय और अन्य सभी शक्तिशाली शक्तियों पर काबू पाने के लिए शुभ, समृद्धि, ज्ञान लाता है।",
//                    "काली माता देवी के भयभीत और क्रूर रूप है। काली पूजा हमारे अहंकार और सभी नकारात्मक प्रवृत्तियों को कम करने में मदद करती है जो आध्यात्मिक प्रगति और भौतिक समृद्धि में बाधा डालती हैं।",
//                    "नवग्रह पूजा परंपरागत रूप से नौ ग्रहों के लिए किया जाता है जो अत्यधिक सकारात्मक ऊर्जा पैदा करता है, और हमारे जीवन में किसी भी स्थिति का सामना करने के लिए हमें साहस प्राप्त करने में मदद करता है। वैदिक ज्योतिष के अनुसार, मानव जन्म के समय ग्रह की स्थिति पीस कार्यों और जीवन की विभिन्न स्थितियों की प्रतिक्रिया पर प्रभाव पड़ेगी। किसी व्यक्ति के जन्म के समय अनुचित ग्रह संरेखण को 'दोष' या नकारात्मक प्रभाव कहा जाता है। नगा गढ़ पूजा नकारात्मक प्रभावों को कम करने में मदद करती है और शांति और समृद्धि डालती है। ",
//            "गायत्री पूजा हमें अपने परिवार और समाज के साथ सकारात्मक संबंध बनाने में मदद करती है, और यह हमें उच्च लक्ष्यों तक पहुंचने में सहायता करता है।",
//            "होली या फागवाह एक लोकप्रिय वसंत महोत्सव है होली ने भगवान विष्णु के भक्त प्रह्लाद द्वारा दानव होलिका की हत्या का स्मरण किया। इस प्रकार, त्यौहार का नाम संस्कृत के शब्द \"होलिकिका डहनम\" से लिया गया है, जिसका शाब्दिक अर्थ है \"होलिकिका का वध\" त्योहार गोवा में शिगमो और शिमगा तथा ग्रामीण महाराष्ट्र में क्रमशः कहा जाता है।",
//            "दीपावली जिसका अर्थ है \"रोशनी की रोशनी\" कन्नड़ और तेलुगू में और मराठी और संस्कृत को उत्तर भारत में \"दीवाली\" कहा जाता है, दीपा का अर्थ है दीपक और हिंदी में दीपक को ज्यादातर दीया या डी कहा जाता है। त्योहार भगवान कृष्ण और उनकी पत्नी सत्यभामा के अवसर पर मनाया जाता है, जिसमें राक्षस नारकासुरा एक और कहानी कहती है कि 14 साल के निर्वासन के बाद अयोध्या में राम और सीता की वापसी के लिए त्योहार मनाया जाता है।"+
//                    "राम को 14 साल के लिए जंगल में निर्वासित किया गया है, उसकी समर्पित पत्नी सीता और विनम्र भाई लक्ष्मण ने उनसे जुड़ने का फैसला किया है, 14 साल बाद पूरे गांव जानता है कि वे उन्हें, उनकी पत्नी और भाई घर की मार्गदर्शन करने के लिए इतनी रोशनी या 'दिवस' लौट रहे हैं। तो हर साल दीपक जंगल में निर्वासन के लिए भेजा जा रहा है की कठोर सजा के बाद घर वापस अपने रास्ते खोजने के लिए राम का प्रतिनिधित्व करने के लिए जलाई हैं।",
//            "नवरात्रि पूजा और नृत्य का हिंदू त्योहार है। संस्कृत में शब्द का शाब्दिक अर्थ है \"नौ रातों\" इस उत्सव के दौरान शक्ति के रूपों की पूजा की जाती है। शाब्दिक रूप से \"नौ रातों\", नव चंद्र दिवस से अश्विन के नौवें दिन तक यह नौ दिन की अवधि हिंदू कैलेंडर का सबसे शुभ समय माना जाता है और इसलिए वर्ष का सबसे मनाया समय है। यद्यपि इसका भारत के विभिन्न हिस्सों में अलग-अलग नाम हैं, लेकिन सभी क्षेत्रों के हिंदू इसे जश्न मनाते हैं। कश्मीर से उत्तर में तमिलनाडु के दक्षिण में, और पश्चिम में गुजरात से पश्चिम में सिक्किम तक, यह बहुत उत्साह के साथ मनाया जाता है क्योंकि बुराई पर अच्छाई की जीत है। प्रत्येक क्षेत्र की इसकी मिथकों और इसकी व्याख्या करने के कारण हैं। नौ दिनों के दौरान देवी के नौ विभिन्न पहलुओं की पूजा की जाती है।",
//
//    };

    private ListView lv;

    // Listview Adapter
    ArrayAdapter<String> adapter;

    // Search EditText
    EditText inputSearch;

    TextToSpeech textToSpeech;
    // ArrayList for Listview
    ArrayList<HashMap<String, String>> productList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pooja_paath_frag, container, false);
        textToSpeech = new TextToSpeech(getActivity(), this);
        // Listview Data
        final String products[] = {"Ganapathy Puja", "Maha Lakhmi Puja", "Saraswathi Puja",
                "Durga Puja",
                "Kali Puja",
                "Nava graha Puja",
                "Gayathri Puja"};

        lv = (ListView) view.findViewById(R.id.list_view);
        inputSearch = (EditText) view.findViewById(R.id.inputSearch);

        // Adding items to listview
        adapter = new ArrayAdapter<String>(getActivity(), R.layout.pooja_paath_item, R.id.poojaname, products);
        lv.setAdapter(adapter);

        /**
         * Enabling Search Filter
         * */


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Toast.makeText(getActivity(), "item position   " + pos, Toast.LENGTH_SHORT).show();
                Pdfview_Fragment fragment = new Pdfview_Fragment();
                getFragmentManager().beginTransaction().replace(R.id.framelayout, fragment).addToBackStack(null).commit();
                int i = 0;
                if (i == TextToSpeech.SUCCESS) {

                    int result = textToSpeech.setLanguage(new Locale("hi"));

                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "This Language is not supported");
                    } else {

                        String text=products[pos].toString();
                        textToSpeech.speak(text,TextToSpeech.QUEUE_FLUSH,null);



                    }

                } else {
                    Log.e("TTS", "Initilization Failed!");
                }
            }
        });


        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                cs.toString().toLowerCase();
                adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });


        return view;
    }

    @Override
    public void onInit(int i) {


    }




}



