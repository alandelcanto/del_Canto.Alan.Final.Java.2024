/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package crud_productos;

/**
 *
 * @author usuario
 */
public abstract interface Ajustable {
    public void ajustar(int ajuste) throws AjusteImposibleException;
}
