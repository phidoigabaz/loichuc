package com.smile.wish.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.smile.wish.Common;
import com.smile.wish.R;
import com.smile.wish.WishActivity;
import com.smile.wish.adapter.AdapterRecyclerChoiceFont;
import com.smile.wish.adapter.AdapterRecyclerChoiceWish;
import com.smile.wish.databinding.FragmentTabchoiceWishBinding;

import java.util.ArrayList;

public class TabListTextWishFragment extends Fragment {
    private FragmentTabchoiceWishBinding binding;
    private AdapterRecyclerChoiceWish adapterRecyclerChoiceWish;
    private OnFragmentManager listener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tabchoice_wish, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.rvFont.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        ArrayList<String> listWish = new ArrayList<>();
        /*listWish.add("Merry Christmas! Gửi em nụ cười vào mỗi buổi sớm, gửi em một đóa hoa cho ngày của em thêm rực rỡ và gửi em tình yêu khi gió đông về");
        listWish.add("Chúc cô gái của anh một mùa giáng sinh ấm áp. Mãi luôn yêu em");
        listWish.add("Đôi ba câu chúc giáng sinh ngắn gọn chưa bao giờ là đủ để dành cho em. Anh luôn mong rằng tình cảm của đôi mình mãi trọn vẹn");
        listWish.add("Chúc cô gái của anh một mùa giáng sinh an yên! Dù em có ở nơi đâu, anh vẫn sẽ dõi theo và đồng hành cùng em. Mãi bên em và yêu em!");
        listWish.add("Đông đã về, nhưng không hề lạnh vì có đôi mình ở bên. Chúc anh một mùa giáng sinh vui vẻ và hạnh phúc bên gia đình. Mãi thương anh!");
        listWish.add("Giáng sinh năm nay không có anh cạnh bên, dù mình xa nhau, nhưng em vẫn luôn nghĩ về anh. Giáng sinh vui vẻ và hãy nhớ tới em anh nhé!");
        listWish.add("Nhân dịp giáng sinh đã về, chúc anh luôn bình an và đạt được nhiều thành công trong sự nghiệp. Mãi luôn đứng cạnh bên và ủng hộ anh!");
        listWish.add("Dù cho mình có ở xa nhau. Giáng sinh năm nay vẫn gợi nhớ anh về những mùa noel ta gần bên. Chúc em yêu giáng sinh vui vẻ. Mong ngày em về!");
        listWish.add("Mùa đông lạnh đến rồi, em đừng quên mặc áo ấm và khi ngủ hãy mơ về anh. Chúc em và gia đình một mùa giáng sinh an nhiên và đầm ấm.");
        listWish.add("Đông đã tới và Noel sắp đến. Chúc người con gái anh thương luôn vui tươi, xinh đẹp như em vốn có. Chúc cho giáng sinh của chúng ta mãi có nhau! Merry Christmas!");
        listWish.add("Mùa đông Hà Nội không có tuyết, không có vẻ đẹp pha lê trắng. Nhưng chỉ có nơi đây – nơi có em, anh mới cảm nhận được mùa giáng sinh lãng mạn đến nhường nào! Mãi dõi theo bước chân em đi và là người sẻ chia niềm vui nỗi buồn cùng em!");
        listWish.add("Đông đã tới và Noel sắp đến. Chúc người con gái anh thương luôn vui tươi, xinh đẹp như em vốn có. Chúc cho giáng sinh của chúng ta mãi có nhau! Merry Christmas!");

        listWish.add("Chúc con một mùa Noel hạnh phúc. Mãi luôn vui tươi và khỏe mạnh như bây giờ con nhé!");
        listWish.add("Con mãi là niềm tự hào của bố mẹ. Mong cuộc sống của con luôn tràn đầy niềm hạnh phúc. Mãi yêu con.");
        listWish.add("Con là niềm vui, niềm hạnh phúc nhất của bố mẹ. Gửi cho con tình yêu và những lời chúc giáng sinh Merry Christmas tuyệt vời nhất! Thương con nhiều.");
        listWish.add("Giáng sinh đã về nhưng căn nhà nhỏ của gia đình vẫn luôn ấm áp. Chúc cô gái nhỏ của bố mẹ mãi luôn xinh xắn, đáng yêu và hạnh phúc thật nhiều con nhé!");
        listWish.add("Chúc con một mùa Noel hạnh phúc. Mãi luôn vui tươi và khỏe mạnh như bây giờ con nhé!");
        listWish.add("Không chỉ là công chúa nhỏ của ba mẹ, tương lai con sẽ trở thành một cô gái tuyệt vời. Chúc giáng sinh của con thật nhiều niềm vui và quà tặng! Mãi yêu con.");
        listWish.add("Con là đóa hoa, là thứ quả ngọt ngào nhất đối với bố mẹ. Gửi thật nhiều lời chúc giáng sinh và năm mới an vui và tuyệt vời nhất dành cho con!");
        listWish.add("Nhân dịp Noel, mẹ chúc con trai luôn mạnh khỏe, vui tươi và học tập thật tốt con nhé! ");
        listWish.add("Qua giáng sinh và năm mới sắp tới, con lại thêm một tuổi. Chúc chàng trai của bố mẹ luôn khỏe mạnh và cứng cỏi trên đường đời.");
        listWish.add("Ngàn vạn lời chúc giáng sinh cũng không thể hiện được hết tình yêu của bố mẹ dành cho con. Chúc cho con có thật nhiều hạnh phúc và sức khỏe. Luôn bên con và ủng hộ con!");
        listWish.add("Đôi ba lời chúc giáng sinh ngắn gọn chắc chắn không thể hiện được trọn vẹn tình yêu bố mẹ dành cho con. Chỉ mong con nhớ bố mẹ luôn ở bên con. ");

        listWish.add("Chúc bạn của tôi mùa Giáng sinh ấm áp và vui vẻ bên cạnh một trái tim yêu thương và hạnh phúc bên cạnh bờ vai của người thương trong đêm Giáng sinh nhé. Merry Christmas.");
        listWish.add("Chúc bạn của tôi mùa Giáng sinh an lành, nhận được nhiều quà và lời chúc.");
        listWish.add("Chúc bạn và gia đình mùa Giáng sinh an lành, hạnh phúc, chúc năm mới thành công tràn ngập.");
        listWish.add("Hạnh phúc không phải là sở hữu chiếc xế hộp sang trọng, chiếc đồng hồ đắt tiền mà hạnh phúc của tôi đơn giản là có người bạn như bạn. Merry Christmas.");
        listWish.add("Merry Christmas. Giáng sinh an lành nhé bạn của tôi.");
        listWish.add(" Noel đến rồi, hi vọng bạn sẽ nhận được những lời chúc tốt đẹp nhất. Chúc bạn Giáng sinh an lành, vui vẻ và may mắn. Merry Christmas.");*/
        SharedPreferences sharedPref = getContext().getSharedPreferences(
                Common.NAME_SHAPERENT, Context.MODE_PRIVATE);
        int iDCategory = sharedPref.getInt(Common.KEY_ID_CATEGORY, Common.valueByEvent());
        if (iDCategory == 1) {
            listWish.add("Xuân này hơn hẳn mấy xuân qua. Phúc lộc đưa nhau đến từng nhà. Vài lời cung chúc tân niên mới. Vạn sự an khang vạn sự lành");
            listWish.add("Hoa đào nở, chim én về, mùa xuân lại đến. Chúc một năm mới: nghìn sự như ý, vạn sự như mơ, triệu sự bất ngờ, tỷ lần hạnh phúc.");
            listWish.add("Chúc bạn 12 tháng phú quý, 365 ngày phát tài, 8760 giờ sung túc, 525600 phút thành công 31536000 giây vạn sự như ý. Đây là lời chúc năm mới về thời gian.");
            listWish.add("Năm hết tết đến kính chúc mọi người thật nhiều sức khoẻ, miệng cười vui vẻ, tiền vào mạnh mẽ, cái gì cũng được suôn sẻ, để sống tiếp một cuộc đời thật là đẹp đẽ.");
            listWish.add("Cung chúc tân xuân phước vĩnh cửu – Chúc trong gia quyến được an khương – Tân niên lai đáo đa phú quý – Xuân đến an khương vạn thọ tường. Đây là lời chúc mừng năm mới thể hiện chút hán nôm mà các cụ hay dùng chúc nhau rất nho nhã.");
            listWish.add("Kính chúc mọi người một năm mới tràn đầy niềm vui và hạnh phúc: Vui trong sức khoẻ, trẻ trong tâm hồn, khôn trong lý tưởng và trưởng thành mọi lĩnh vực.");
            listWish.add("Đong cho đầy Hạnh phúc. Gói cho trọn Lộc tài. Giữ cho mãi An Khang. Thắt cho chặt Phú quý. Cùng chúc nhau Như ý, Hứng cho tròn An Khang, Chúc năm mới Bình An. Cả nhà đều Sung túc.");
            listWish.add("Chúc bạn có 1 bầu trời sức khỏe, 1 Biển cả tình thương, 1 Đại dương tình bạn, 1 Điệp khúc tình yêu, 1 Người yêu chung thủy, 1 Sự nghiệp sáng ngời, 1 Gia đình thịnh vượng. – Chúc năm mới cả gia đình bạn vạn sự như ý, Tỉ sự như mơ, Triệu triệu bất ngờ, Không chờ cũng đến!");
            listWish.add("Con kính chúc ba má sức khỏe dồi dào, lúc nào cũng “tươi trẻ” như thời mới yêu. Mong nhà mình lúc nào cũng quây quần, chị em chúng con luôn được nghe tiếng cười đùa vui vẻ của ba má.");
            listWish.add("Một mùa xuân mới đã đến, con kính chúc ba mẹ luôn khỏe mạnh, hạnh phúc bên chúng con và đón một năm mới an lành, sung túc và tràn ngập hạnh phúc.");
            listWish.add("Một năm qua có biết bao niềm vui, nỗi buồn nhưng thật hạnh phúc bởi dù có chuyện gì thì vợ chồng mình vẫn luôn ở cạnh nhau, cùng nhau cố gắng vượt qua. Mãi là chỗ dựa vững chắc cho vợ, chồng yêu nhé!");
            listWish.add("Bây giờ anh đã yêu em, hôm nay và ngày mai nữa, và chúa đã cho anh thời gian..Anh sẽ yêu em đến cuối cuộc đời. Chúc tình yêu của anh một năm mới hạnh phúc và may mắn.");
            listWish.add("Thà để giọt mồ hôi rơi trên trang sách, còn hơn giọt nước mắt rớt trên đề thi. Chúc năm mới tự tin và chiến thắng.");
            listWish.add("Chúc mọi điều tốt đẹp trong năm mới nhé. Happy new year!");
            listWish.add("Chúc bạn luôn vui vẻ, bình an và hạnh phúc trong năm mới!");
            listWish.add("1 năm mới, 1 tuổi mới, nhiều bạn mới, nhiều hiểu biết mới và 1 lời chúc: Mãi mãi hạnh phúc bên gia đình và những người thân yêu nhất.");
            listWish.add("Mọi thứ lại bắt đầu khi năm mới đang đến. Chúc bạn năm mới đầy hạnh phúc và những tháng đầy triển vọng nhé");
            listWish.add("Mong rằng năm mới sẽ mang sự bình yên và phát đạt đến cho bạn.");
            listWish.add("Đã đến lúc phải nói lời tạm biệt với năm cũ. Hãy cùng nhau đón chào năm mới với trái tim tích cực và tràn đầy hy vọng mới. Chúc mừng năm mới!");
            listWish.add("Cung chúc tân xuân: Thuận buồm xuôi gió, an khang thịnh vượng!");
            listWish.add("Đầu xuân năm mới BÌNH AN, chúc luôn TUỔI TRẺ chúc AN KHANG.");
            listWish.add("Năm hết Tết đến. Đón hên về nhà. Quà cáp bao la. Một nhà không đủ. Vàng bạc đầy tủ. Gia chủ phát tài");
            listWish.add("Thần tài rảo bước khắp mọi nhà. Tiền lộc đầy ắp, xuân hạnh phúc. Mọi người xum họp vui năm mới.");
            listWish.add("Thịt mỡ dưa hành câu đối đỏ\n" +
                    "Cây nêu tràng pháo bánh chưng xanh.");
            listWish.add("Xuân an khang đức tài như ý\n" +
                    "Niên thịnh vượng phúc thọ vô biên.");
            listWish.add("Hoa khai phú quý\n" +
                    "Trúc báo bình an");
            listWish.add("Đông nghinh mai chí\n" +
                    "Xuân bạn yến quy");
            listWish.add("Ngàn lần như ý\n" +
                    "Vạn sự như mơ");
            listWish.add("Chúc Tết đến trăm điều như ý\n" +
                    "Mừng xuân sang vạn sự thành công");
            listWish.add("Tăng phúc tăng quyền tăng phú quý\n" +
                    "Tấn tài tấn lộc tấn vinh hoa");
            listWish.add("Ngoài ngõ mừng xuân nghênh phúc lộc\n" +
                    "Trong nhà vui Tết đón bình an");
            listWish.add("Tết đến gia đình vui sum họp\n" +
                    "Xuân về con cháu hưởng bình an");
        } else if (iDCategory == 2) {
            listWish.add("Hãy gác lại quá khứ, hướng đến tương lai. Những gì tốt đẹp nhất vẫn đang đến.\n" +
                    "Chúc bạn một tuổi mới như thế ^^");
            listWish.add("Chúc mừng sinh nhật.\n" +
                    "Chúc bạn luôn xinh đẹp và hạnh phúc, không những trong ngày đặc biệt này, mà mãi về sau. Hãy luôn yêu đời nhé !");
            listWish.add("Chúc bạn một ngày sinh nhật thật đặc biệt.\n" +
                    "Một ngày được phủ tràn nắng ấm, tiếng cười và hạnh phúc.");
            listWish.add("Hôm nay sẽ là khởi đầu của những ngày tuyệt vời trong năm mới. Chúc mừng sinh nhật!");
            listWish.add("Chúc bạn có một sinh nhật hạnh phúc bên bạn bè và người thân. Tận hưởng những điều bất ngờ cuộc sống ban tặng nhé!");
            listWish.add("Hãy lạc quan, luôn tin tưởng vào bản thân, bạn nhé!\n" +
                    "Chúc mừng sinh nhật.");
            listWish.add("Cuộc đời là một chuyến hành trình. Tận hưởng từng centimet nhé ;)\n" +
                    "Chúc mừng khởi đầu một năm hành trình mới");
            listWish.add("Mừng sinh nhật bạn thân!\n" +
                    "Tụi mình bên nhau từ hồi nhỏ xíu, thật vui là đến giờ vẫn còn bên nhau. Tui đang nghĩ đến một ngày mình cùng ngồi xích đu ở viện dưỡng lão, kể về những ngày nông nỗi tuổi trẻ rồi cười vang :D");
            listWish.add("Điều tuyệt vời nhất khi có bạn là để cùng mình làm những điều rồ dại nhất mà không cần quan tâm đến thế giới. Haha\n" +
                    "Chúc mừng sinh nhật bạn thân!");
            listWish.add("Thêm một tuổi mới, thêm khôn ngoan. Chúc mừng sinh nhật!");
            listWish.add("Hm, ít ra thì bạn cũng đang trưởng thành theo hướng mong muốn đó – một hotgirl ;)\n" +
                    "Chúc mừng sinh nhật người đẹp.");
            listWish.add("Chúc mừng sinh nhật! Chẳng ngôn từ nào diễn tả được tình bạn này có ý nghĩa thế nào với mình :)");
            listWish.add("Party điii…!! Hôm nay là của riêng bạn đó ;)\n" +
                    "Chúc mừng sinh nhật nghen");
            listWish.add("Mừng sinh nhật em! Anh chúc em luôn tươi trẻ mạnh khỏe và hạnh phúc. Mãi mãi là người anh yêu nhất");
            listWish.add("Tặng phẩm này riêng nó chẳng có ý nghĩa gì cả, nhưng mà kỉ niệm ở đây là anh gởi cho em tất cả những tình cảm tha thiết nhất. Sinh nhật vui vẻ.");
            listWish.add("Hôm nay là một ngày thật đặc biệt, không chỉ đối với em là còn là với cả thế giới – Ngày kỉ niệm sự ra đời của một ngôi sao vui vẻ và đáng yêu nhất cơ mà.");
            listWish.add("Em biết không, trái đất đang ngừng xoay 1 giây để chúc mừng sinh nhật em đó.");
            listWish.add("Sinh nhật năm nay năm sau, 10 năm sau hai muơi năm sau 50, 100 năm sau chúng ta vẫn đốt những ngọn nến nung linh này nhé…");
            listWish.add("Nếu không có ngày này thì có lẽ anh cũng không có được em. Happy birthday to mylove!");
            listWish.add("Em yêu ! Sinh nhật của em anh chúc em mạnh khỏe và những may mắn, hạnh phúc nhất sẽ đến với em trong cuộc đời.");
            listWish.add("Sinh nhật mẹ, con chúc mẹ có nhiều sức khỏe để có thể dẫn dắt chúng con đi trên đường đời. Mẹ mãi mãi là người mẹ yêu quý của con, con rất mong luôn có mẹ bên cạnh! Con yêu mẹ.");
            listWish.add("Gửi mẹ yêu của con! Con chẳng biết nói gì hơn, nhân ngày sinh nhật mẹ, con chỉ biết chúc mẹ luôn luôn mạnh khoẻ, hạnh phúc và luôn luôn là người mẹ mà con yêu quý nhất!");
            listWish.add("Happy Birthday! Chúc em gái sinh nhật đầy ắp yêu thuơng và tiếng cười, thêm tuổi, thêm hạnh phúc, thêm nhiều niềm zui nhé!!!");
            listWish.add("Chúc mình sinh nhật ba. Chúc ba mạnh khỏe, dẻo dai, tràn đầy tình yêu thương, yêu đời… Gia đình mình luôn bên cạnh và cổ vũ cho ba. Thương ba hơn tất cả mọi thứ.");
        }else {
            listWish.add("Ngốc à! Thế là một Valentin nữa lại đến rồi. Tình yêu đã trải qua rất nhiều thử thách để được hạnh phúc như ngày hôm nay. Anh rất hạnh phúc khi được ở bên Em, lo lắng - yêu thương Em");
            listWish.add("Nhân ngày Lễ tình yêu, Anh muốn nói với Em rằng: Anh mãi bên Em và ngày càng yêu Em nhiều hơn. Hãy tin ở Anh, Em nhé!");
            listWish.add("Châu Âu ngủ, Châu Á cũng đang ngủ, Châu Mỹ đang tối dần, chỉ có đôi mắt đẹp nhất trên thế giới này đang đọc tin nhắn của anh. Happy Valentine Day");
            listWish.add("Anh sẽ luôn nắm tay em ở chỗ đông người, không phải vì sợ em lạc mất, mà để mọi người nhìn vào trầm trồ rằng “hai đứa nó đang yêu nhau đấy”");
            listWish.add("Anh sẽ ngồi im nhìn em khóc và làm nhiệm vụ tiếp tế khăn giấy cho em mỗi khi chúng mình xem phim buồn, dù anh thấy cái cảnh ấy chả có gì đáng rỏ nước mắt cả");
            listWish.add("Trên thiên đường có 10 thiên thần: 5 thiên thần đang chơi đùa, 4 thiên thần đang nói chuyện và 1 thiên thần đang đọc tin nhắn này…");
            listWish.add("Anh muốn nói rằng anh yêu và nhớ em rất nhiều… Nhưng anh hứa chúng ta sẽ cùng ăn mừng ngày này với thật nhiều tình yêu và hạnh phúc");
            listWish.add("Chúng ta đã cùng vượt qua những khoảng thời gian khó khăn, em yêu. Anh chỉ muốn cho tất cả mọi người biết rằng: em là duy nhất của anh… Anh yêu em rất nhiều.");
            listWish.add("Anh, người yêu của em. Em biết rằng mình đã chờ đợi anh “mấy chục” năm nay và anh cũng như vậy. Chúng ta vẫn tiếp tục cuộc hành trình để cuối cùng sẽ có một ngày chúng ta nhận ra nhau.");
            listWish.add("Em mong rằng em sẽ nhận được món quà rất lớn từ anh – Đó chính là tình yêu của anh, có được không anh?");
            listWish.add("Xin chào bạn. Đây là tổng đài tin nhắn. Ấn phím 1 để có 1 lời khen. Phím 2 cho một lời chúc tốt đẹp. Phím 3 cho 1 nụ hôn. Phím 4 cho 1 cuộc hẹn. Nếu muốn tất cả hãy bấm số của tôi");
            listWish.add("Anh à. Em chỉ là một cô gái bình thường, nên cái em cần là hạnh phúc không đau thương. Đừng yêu em như 1 việc phải làm anh mà hãy yêu thương em bằng cả trái tim anh nhé. Yêu anh !!!");

            listWish.add("In your eyes, I see tomorrow…In your arm I found love… Love you to day and always");
            listWish.add("If I never met you, I wouldn’t like you. If I didn’t like you, I would not love you. If I didn’t love you, I wouldn’t miss you. But I did, I do and I will. Love you so much…kisses.");
            listWish.add("I have a heart and it’s true. But now it’s gone from me to you. So, care for it, just like I do. Because I have no heart and you have two…");
            listWish.add("I didn’t think that I could ever believe in happiness until I met you. Happy Valentine’s Day!");
            listWish.add("I just want to say that I Love You A lot and I’m Missing You…But i promise that we’ll celebrate this day with lots of love and happiness..");
            listWish.add("I wanna tell you that you are my life. I love you from bottom of my heart. I can’t stay without you. You are my valentine.");

            listWish.add("A day may start or end without a message from me, but believe me it won”t start or end without me thinking of you");
            listWish.add("I will walk with you side by side for only one condition: hide your wings every time we walk together because the whole world might know that you’re my angel!");
            listWish.add("If I could change the alphabet, I would put U and I together!");
        }


        adapterRecyclerChoiceWish = new AdapterRecyclerChoiceWish(listWish, getContext());
        binding.rvFont.setAdapter(adapterRecyclerChoiceWish);
        adapterRecyclerChoiceWish.setOnItemClickedListener(new AdapterRecyclerChoiceWish.OnItemClickedListener() {
            @Override
            public void onItemClick(String wish) {
                listener.onDataSelected(wish);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof WishActivity)
            this.listener = (OnFragmentManager) context; // gan listener vao MainActivity
        else
            throw new RuntimeException(context.toString() + " must implement onViewSelected!");
    }

    public interface OnFragmentManager {
        void onDataSelected(String data); // ở đây các bạn truyền dữ liệu cần chuyển qua Fragment kia nhé
    }
}
