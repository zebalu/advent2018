package day14;

import static org.junit.Assert.*

import org.junit.Test

import spock.lang.Specification

class RecipeGeneratorTest extends Specification {

	def example() {
		RecipeGenerator rg = new RecipeGenerator()
		while(rg.recipeCount() < 2028) {
			rg.round()
		}
		
		expect:
		rg.last10DigitsAfter(9) == '5158916779'
		rg.last10DigitsAfter(5) == '0124515891'
		rg.last10DigitsAfter(18) == '9251071085'
		rg.last10DigitsAfter(2018) == '5941429882'
	}
	
	def input() {
		RecipeGenerator rg = new RecipeGenerator()
		while(rg.recipeCount() < 503761+10) {
			rg.round()
		}
		
		expect:
		rg.last10DigitsAfter(503761) == '1044257397'
		
	}
	
	def exampleTill(String score, int steps) {
		RecipeGenerator rg = new RecipeGenerator()
		rg.generateTillScore(score)
		
		expect:
		rg.recipeCount()-score.size() == steps
		
		where:
		score   | steps
		'51589' | 9
		'01245' | 5
		'92510' | 18
		'59414' | 2018
	}
	
	def exampleTillIndex(String score, int steps) {
		RecipeGenerator rg = new RecipeGenerator()
		rg.generateTillScore(score)
		
		expect:
		rg.recipesBefore(score) == steps
		
		where:
		score   | steps
		'51589' | 9
		'01245' | 5
		'92510' | 18
		'59414' | 2018
	}
	/*
	def inputTill(String score, int steps) {
		RecipeGenerator rg = new RecipeGenerator()
		rg.generateTillScore(score)
		
		expect:
		rg.recipesBefore(score) == steps
		
		where:
		score    | steps
		'503761' | 20185425
	}
	*/
}
