package com.devband.tronwalletforandroid.ui.blockexplorer.transfer;

import com.devband.tronlib.TronNetwork;
import com.devband.tronlib.dto.Transfer;
import com.devband.tronlib.dto.Transfers;
import com.devband.tronwalletforandroid.common.AdapterDataModel;
import com.devband.tronwalletforandroid.ui.mvp.BasePresenter;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class TransferPresenter extends BasePresenter<TransferView> {

    private AdapterDataModel<Transfer> mAdapterDataModel;

    public TransferPresenter(TransferView view) {
        super(view);
    }

    public void setAdapterDataModel(AdapterDataModel<Transfer> adapterDataModel) {
        this.mAdapterDataModel = adapterDataModel;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }

    public void getTransfer(long startIndex, int pageSize) {
        mView.showLoadingDialog();

        TronNetwork.getInstance()
                .getTransfers(startIndex, pageSize, "-timestamp", true)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Transfers>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Transfers transactions) {
                        mAdapterDataModel.addAll(transactions.getData());
                        mView.finishLoading(transactions.getTotal());
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showServerError();
                    }
                });
    }

    public void getTransfer(long block, long startIndex, int pageSize) {
        mView.showLoadingDialog();
        TronNetwork.getInstance()
                .getTransfers("-timestamp", true, pageSize, startIndex, block)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Transfers>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Transfers transfers) {
                        mAdapterDataModel.addAll(transfers.getData());
                        mView.finishLoading(transfers.getTotal());
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showServerError();
                    }
                });
    }
}
