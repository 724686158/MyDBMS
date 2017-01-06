package database;

import java.io.Serializable;
import java.util.logging.Level;

/**
 * Created by 离子态狍子 on 2016/11/26.
 */
public class Index implements Serializable {
    private int mMaxCount;
    private int mKeyLength;
    private int mKeyType;
    private int mRank;
    private int mRubbish;
    private int mRoot;
    private int mLeafHead;
    private int mKeyCount;
    private int mLevel;
    private int mNodeCount;
    private String mAttributeName;
    private String mName;

    public Index() {
    }

    public Index(String name, String attributeName, int keyType, int keyLen, int rank) {
        this.mName = name;
        this.mAttributeName = attributeName;
        this.mKeyCount = 0;
        this.mLevel = -1;
        this.mRoot = -1;
        this.mLeafHead = -1;
        this.mKeyType = keyType;
        this.mKeyLength = keyLen;
        this.mRank = rank;
        this.mRubbish = -1;
        this.mMaxCount = 0;
    }


    public int getmMaxCount() {
        return mMaxCount;
    }

    public void setmMaxCount(int mMaxCount) {
        this.mMaxCount = mMaxCount;
    }

    public int getmKeyLength() {
        return mKeyLength;
    }

    public void setmKeyLength(int mKeyLength) {
        this.mKeyLength = mKeyLength;
    }

    public int getmKeyType() {
        return mKeyType;
    }

    public void setmKeyType(int mKeyType) {
        this.mKeyType = mKeyType;
    }

    public int getmRank() {
        return mRank;
    }

    public void setmRank(int mRank) {
        this.mRank = mRank;
    }

    public int getmRubbish() {
        return mRubbish;
    }

    public void setmRubbish(int mRubbish) {
        this.mRubbish = mRubbish;
    }

    public int getmRoot() {
        return mRoot;
    }

    public void setmRoot(int mRoot) {
        this.mRoot = mRoot;
    }

    public int getmLeafHead() {
        return mLeafHead;
    }

    public void setmLeafHead(int mLeafHead) {
        this.mLeafHead = mLeafHead;
    }

    public int getmKeyCount() {
        return mKeyCount;
    }

    public void setmKeyCount(int mKeyCount) {
        this.mKeyCount = mKeyCount;
    }

    public int getmLevel() {
        return mLevel;
    }

    public void setmLevel(int mLevel) {
        this.mLevel = mLevel;
    }

    public int getmNodeCount() {
        return mNodeCount;
    }

    public void setmNodeCount(int mNodeCount) {
        this.mNodeCount = mNodeCount;
    }

    public String getmAttributeName() {
        return mAttributeName;
    }

    public void setmAttributeName(String mAttributeName) {
        this.mAttributeName = mAttributeName;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public int IncreaseMaxCount(){
        return mMaxCount++;
    }

    public int IncreaseKeyCount(){
        return mKeyCount++;
    }

    public int IncreaseNodeCount(){
        return mNodeCount++;
    }

    public int IncreaseLevel(){
        return mLevel++;
    }

    public int DecreaseKeyCount(){
        return mKeyCount--;
    }

    public int DecreaseNodeCount(){
        return mNodeCount--;
    }

    public int DecreaseLevel(){
        return mLevel--;
    }

}
