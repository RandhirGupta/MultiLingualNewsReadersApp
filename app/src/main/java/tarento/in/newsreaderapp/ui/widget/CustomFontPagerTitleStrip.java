package tarento.in.newsreaderapp.ui.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.view.PagerTitleStrip;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Randhir on 7/23/2015.
 */
public class CustomFontPagerTitleStrip extends PagerTitleStrip {
    //Moved typeface instantiation here, only required once.
    Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "Devnew.ttf");

    private boolean mCanSetFont = false;
    private boolean mDrawFullUnderLine;
    private boolean mDrawFullUnderLineSet = false;

    public CustomFontPagerTitleStrip(Context context) {
        super(context);
    }

    public CustomFontPagerTitleStrip(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setCustomFont() {
        mCanSetFont = true;
    }
//    public void setDrawFullUnderLine(Boolean drawFull){
//        mDrawFullUnderLine = drawFull;
//        mDrawFullUnderLineSet = true;
//        invalidate();
//    }
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (mCanSetFont) {
            for (int i = 0; i < this.getChildCount(); i++) {
                if (this.getChildAt(i) instanceof TextView) {
                    ((TextView) this.getChildAt(i)).setTypeface(tf);
                }
            }
        }
    }
}