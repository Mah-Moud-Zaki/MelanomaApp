package com.example.final_project.nada;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.final_project.R;

import java.util.ArrayList;

public class BasalCellCarcinomaActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_melanoma);



        final ArrayList<BasalCellCarcinoma> arrayList = new ArrayList<BasalCellCarcinoma>();

       // getSupportActionBar().setTitle("Melanoma");
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // add all the values from 1 to 15 to the arrayList
        // the items are of the type NumbersView
        //---------------Melanoma-------------
        arrayList.add(new BasalCellCarcinoma( "Overview", "\t\t Basal cell carcinoma is a type of skin cancer. Basal cell carcinoma begins in the basal cells — a type of cell within the skin that produces new skin cells as old ones die off.\n\n" +
                "\t\t Basal cell carcinoma often appears as a slightly transparent bump on the skin, though it can take other forms. Basal cell carcinoma occurs most often on areas of the skin that are exposed to the sun, such as your head and neck.\n\n" +

                "\t\t Most basal cell carcinomas are thought to be caused by long-term exposure to ultraviolet (UV) radiation from sunlight. Avoiding the sun and using sunscreen may help protect against basal cell carcinoma."));
        arrayList.add(new BasalCellCarcinoma("Symptoms" ,"\t\t Basal cell carcinoma usually develops on sun-exposed parts of your body, especially your head and neck. This skin cancer appears less often on the trunk and legs, and basal cell carcinoma can — but rarely — occur on parts of your body usually protected from the sun such as genitals or women's breasts.\n\n" +
                "\t\t Basal cell carcinoma appears as a change in the skin, such as a growth or a sore that won't heal. These changes in the skin, or lesions, usually have one of the following characteristics:\n\n" +
                "•\tA pearly white, skin-colored or pink bump that is translucent, meaning you can see a bit through the surface. Tiny blood vessels are often visible. In people with darker skin tones, the lesion would be darker but still somewhat translucent. The most common type of basal cell carcinoma, this lesion often appears on the face, ears or neck. The lesion may rupture, bleed and scab over.\n\n" +
                "•\tA brown, black or blue lesion — or a lesion with dark spots — with a slightly raised, translucent border.\n\n" +
                "•\tA flat, scaly, reddish patch with a raised edge is more common on the back or chest. Over time, these patches can grow quite large.\n\n" +
                "•\tA white, waxy, scar-like lesion without a clearly defined border, called morpheaform basal cell carcinoma, is the least common. This lesion is easy to overlook, but it may be a sign of a particularly invasive and disfiguring cancer."));

        arrayList.add(new BasalCellCarcinoma("When to see a doctor?" , "\t\t Make an appointment with your doctor if you observe changes in the appearance of your skin, such as a new growth, a change in a previous growth or a recurring sore."));

        arrayList.add(new BasalCellCarcinoma("Causes" , "\t\t Basal cell carcinoma occurs when one of the skin's basal cells develops a mutation in its DNA.\n\n" +
                "\t\t Basal cells are found at the bottom of the epidermis — the outermost layer of skin. Basal cells produce new skin cells. As new skin cells are produced, they push older cells toward the skin's surface, where the old cells die and are sloughed off.\n\n" +
                "\t\t The process of creating new skin cells is controlled by a basal cell's DNA. A mutation in the DNA causes a basal cell to multiply rapidly and continue growing when it would normally die. Eventually the accumulating abnormal cells may form a cancerous tumor — the lesion that appears on the skin."));

        arrayList.add(new BasalCellCarcinoma("Ultraviolet light and other causes" , "\t\t Much of the damage to DNA in basal cells is thought to result from ultraviolet (UV) radiation found in sunlight and in commercial tanning lamps and tanning beds. But sun exposure doesn't explain skin cancers that develop on skin not ordinarily exposed to sunlight. Other factors can contribute to the risk and development of basal cell carcinoma, and the exact cause may in some cases not be clear."));

        arrayList.add(new BasalCellCarcinoma("Risk Factors" , "\t\t Factors that increase your risk of basal cell carcinoma include:\n\n" +
                "•\tChronic sun exposure. A lot of time spent in the sun — or in commercial tanning booths — increases the risk of basal cell carcinoma. The threat is greater if you live in a sunny or high-altitude location, both of which expose you to more UV radiation. Severe sunburn, especially during childhood or adolescence, also increases your risk.\n\n" +
                "•\tRadiation therapy. Radiation therapy to treat psoriasis, acne or other skin conditions may increase the risk of basal cell carcinoma at previous treatment sites on the skin.\n\n" +
                "•\tFair skin. The risk of basal cell carcinoma is higher among people who freckle or burn easily or who have very light skin, red or blond hair, or light-colored eyes.\n\n" +
                "•\tYour sex. Men are more likely to develop basal cell carcinoma than women.\n\n" +
                "•\tYour age. Because basal cell carcinoma often takes decades to develop, the majority of basal cell carcinomas occur after age 50.\n\n" +
                "•\tA personal or family history of skin cancer. If you've had basal cell carcinoma one or more times, you have a good chance of developing it again. If you have a family history of skin cancer, you may have an increased risk of developing basal cell carcinoma.\n\n" +
                "•\tImmune-suppressing drugs. Taking medications that suppress your immune system, especially after transplant surgery, significantly increases your risk of skin cancer. Basal cell carcinoma that develops in people taking immune-suppressing drugs may be more likely to recur or spread to other parts of the body.\n\n" +
                "•\tExposure to arsenic. Arsenic, a toxic metal that's found widely in the environment, increases the risk of basal cell carcinoma and other cancers. Everyone has some arsenic exposure because it occurs naturally in the soil, air and groundwater. But people who may be exposed to higher levels of arsenic include farmers, refinery workers, and people who drink contaminated well water or live near smelting plants.\n\n" +
                "•\tInherited syndromes that cause skin cancer. Certain rare genetic diseases often result in basal cell carcinoma. Nevoid basal cell carcinoma syndrome (Gorlin-Goltz syndrome) causes numerous basal cell carcinomas, as well as disorders of the skin, bones, nervous system, eyes and endocrine glands. Xeroderma pigmentosum causes an extreme sensitivity to sunlight and a high risk of skin cancer because people with this condition have little or no ability to repair damage to the skin from ultraviolet light."));

        arrayList.add(new BasalCellCarcinoma("Complications" , R.drawable.basalcell , "\t\t Complications of basal cell carcinoma can include:\n\n" +
                "•\tA risk of recurrence. Basal cell carcinomas commonly recur. Even after successful treatment, a lesion may reappear, often in the same place.\n\n" +
                "•\tAn increased risk of other types of skin cancer. A history of basal cell carcinoma may also increase the chance of developing other types of skin cancer, such as squamous cell carcinoma.\n\n" +
                "•\tCancer that spreads beyond the skin. Rare, aggressive forms of basal cell carcinoma can invade and destroy nearby muscles, nerves and bone. And rarely, basal cell carcinoma can spread to other areas of the body."));



        arrayList.add(new BasalCellCarcinoma("Prevention" , "\t\tYou may reduce your risk of basal cell carcinoma if you: \n\n " +
                "•\tAvoid the midday sun. Avoid the sun when its rays are the strongest. For most places, this is between about 10 a.m. and 4 p.m. Because the sun's rays are strongest during this period, try to schedule outdoor activities for other times of the day, even in winter. You absorb UV radiation year-round, and clouds offer little protection from damaging rays.\n\n" +
                "•\tUse sunscreen year-round. Choose a sunscreen that blocks both UVA and UVB types of radiation from the sun and has an SPF of at least 15. Apply sunscreen generously, and reapply every two hours — or more often if you're swimming or perspiring. The American Academy of Dermatology recommends using a broad-spectrum sunscreen with an SPF of 30 or more. Even the best sunscreen might be less effective than the SPF number on the bottle would lead you to believe if it isn't applied thoroughly or thickly enough, or if it's perspired away or washed off while swimming.\n\n" +
                "•\tWear protective clothing. Wear protective clothing. Sunscreens don't provide complete protection from UV rays, so wear tightly woven clothing that covers your arms and legs, and a broad-brimmed hat, which provides more protection than a baseball cap or visor does. Some companies also sell photoprotective clothing. Wear sunglasses that provide full protection from both UVA and UVB rays.\n\n" +
                "•\tAvoid tanning beds. Tanning beds emit UV radiation, which can increase the risk of skin cancer.\n\n" +
                "•\tBecome familiar with your skin so that you'll notice changes. Examine your skin so that you become familiar with what your skin normally looks like. This way, you may be more likely to notice any skin changes. With the help of mirrors, check your face, neck, ears and scalp. Examine your chest and trunk, and the tops and undersides of your arms and hands. Examine both the front and back of your legs, and your feet, including the soles and the spaces between your toes. Also check your genital area, and between your buttocks. If you notice anything unusual, talk to your doctor.\n\n" +
                "•\tAsk your doctor about screening. If you've already had skin cancer, you have an increased risk of a second cancer. Talk with your doctor about how often you should be screened for a recurrence."));


                // Now create the instance of the NumebrsViewAdapter and pass
        // the context and arrayList created above

   BasalCellCarcinomaView melanomaView = new BasalCellCarcinomaView(this , arrayList);
        // create the instance of the ListView to set the numbersViewAdapter
        ListView MelanoListView = findViewById(R.id.MelanomaList);

        // set the numbersViewAdapter for ListView
        MelanoListView.setAdapter(melanomaView);

        // calling the action bar
     //   ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
    //    actionBar.setDisplayHomeAsUpEnabled(true);






    }
/*
    // this event will enable the back
    // function to the button on press
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


 */
}