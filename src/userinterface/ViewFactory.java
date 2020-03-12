package userinterface;

import impresario.IModel;
import model.BookCollection;

public class ViewFactory {

    public static View createView(String viewName, IModel model) {
        if (viewName.equals("LibrarianView")) {
            return new LibrarianView(model);
        } else if (viewName.equals("BookView")) {
            return new BookView(model);
        } else if (viewName.equals("PatronView")) {
            return new PatronView(model);
        } else if(viewName.equals("SearchBooksView")) {
            return new SearchBooksView(model);
        }else if(viewName.equals("BookCollectionView")){
            System.out.println("factory");
            return new BookCollectionView(model);
        } else if(viewName.equals("SearchPatronsView")){
            return new SearchPatronsView(model);
        } else if(viewName.equals("PatronCollectionView")){
            return new PatronCollectionView(model);
        } else {
            return null;
        }
    }
}