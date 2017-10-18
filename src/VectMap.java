import java.util.HashMap;

/**
 * VectMap
 */
public class VectMap<K,V> extends HashMap<K,V>{
    
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for(K key : this.keySet())
        {
            sb.append("(K: " + key.toString() + ", V: " + this.get(key).toString() + "), ");
        }
        sb.append("\b");
        return sb.toString();
    }

    public double getEucleadian(VectMap<K,V> vm)
    {
        
    }

    public VectMap<K,V> plus(vVectashMap<K,V> )
}