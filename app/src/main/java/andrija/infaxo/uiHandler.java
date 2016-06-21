package andrija.infaxo;

interface dialogAndPicturePublisher{
    void publishImage(int index, int item);
    void publishDialog(String message);
    void makeToast(String message, boolean Short);
}