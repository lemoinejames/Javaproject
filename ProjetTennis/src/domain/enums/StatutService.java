/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain.enums;

/**
 * Énumération représentant les différents statuts possibles d'un service lors d'un échange.
 */
public enum StatutService {
    ACE,
    FAUTE,
    LET,
    CORRECT, // Le service est bon, l'échange continue
    DOUBLE_FAUTE
}