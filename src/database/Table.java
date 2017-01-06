package database;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 离子态狍子 on 2016/11/25.
 */
public class Table implements Serializable {
    private String mName;
    private int mLength;
    private int mFirstBlock;
    private int mFristRubbish;
    private int mBlockCount;
    private ArrayList<Attribute> mAttributes;
    private ArrayList<Index> mIndexs;

    public Table() {
        this.mName = "";
        this.mLength = -1;
        this.mFirstBlock = -1;
        this.mFristRubbish = -1;
        this.mBlockCount = 0;
        this.mAttributes = new ArrayList<>();
        this.mIndexs = new ArrayList<>();
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public void setmAttributes(ArrayList<Attribute> mAttributes) {
        this.mAttributes = mAttributes;
    }

    public void setmIndexs(ArrayList<Index> mIndexs) {
        this.mIndexs = mIndexs;
    }

    public String getmName() {
        return mName;
    }

    public int getmLength() {
        return mLength;
    }

    public void setmLength(int mLength) {
        this.mLength = mLength;
    }

    public int getmFirstBlock() {
        return mFirstBlock;
    }

    public void setmFirstBlock(int mFirstBlock) {
        this.mFirstBlock = mFirstBlock;
    }

    public int getmFristRubbish() {
        return mFristRubbish;
    }

    public void setmFristRubbish(int mFristRubbish) {
        this.mFristRubbish = mFristRubbish;
    }

    public int getmBlockCount() {
        return mBlockCount;
    }

    public void setmBlockCount(int mBlockCount) {
        this.mBlockCount = mBlockCount;
    }

    public ArrayList<Attribute> getAttributes() {
        return mAttributes;
    }

    public void setAttributes(ArrayList<Attribute> mAttributes) {
        this.mAttributes = mAttributes;
    }

    public ArrayList<Index> getIndexs() {
        return mIndexs;
    }

    public void setIndexs(ArrayList<Index> mIndexs) {
        this.mIndexs = mIndexs;
    }

    public Attribute GetAttributeByName(String name){
        for (Attribute a : this.getAttributes()){
            if(a.getmName().equals(name)){
                return a;
            }
        }
        return null;
    }

    public Index GetIndexByName(String name){
        for(Index i : this.getIndexs()){
            if(i.getmName().equals(name)){
                return i;
            }
        }
        return null;
    }

    public void IncreaseBlockCount(){
        this.mBlockCount++;
    }

    public int getAttributeNumber(){
        return this.mAttributes.size();
    }

    public int getIndexNumber(){
        return this.mIndexs.size();
    }

    public Attribute getAttributeByNumber(int num){
        return this.mAttributes.get(num);
    }

    public Index getIndexByNumber(int num){
        return this.mIndexs.get(num);
    }

    public void AddAtribute(Attribute a){
        this.mAttributes.add(a);
    }

    public void AddIndex(Index i){
        this.mIndexs.add(i);
    }

}
