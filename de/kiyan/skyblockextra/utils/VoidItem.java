package de.kiyan.skyblockextra.utils;

import org.bukkit.entity.Item;

import java.util.ArrayList;
import java.util.List;

import static de.kiyan.skyblockextra.utils.Utils.randInt;

public class VoidItem
{
    private List< Item > iItem;

    public VoidItem( )
    {
        this.iItem = new ArrayList< Item >(  );
    }

    public void addItem( Item item )
    {
        if( randInt( 0, 100 ) > 50 )
            iItem.add( item );
    }

    public void removeItem( Item item )
    {
        if( iItem.contains( item ) )
            iItem.remove( item );
    }

    public List< Item > getItems( )
    {
        return this.iItem;
    }
}
