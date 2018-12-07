require "application_system_test_case"

class LeccionHiraganasTest < ApplicationSystemTestCase
  setup do
    @leccion_hiragana = leccion_hiraganas(:one)
  end

  test "visiting the index" do
    visit leccion_hiraganas_url
    assert_selector "h1", text: "Leccion Hiraganas"
  end

  test "creating a Leccion hiragana" do
    visit leccion_hiraganas_url
    click_on "New Leccion Hiragana"

    fill_in "Hiragana", with: @leccion_hiragana.hiragana_id
    fill_in "Leccion", with: @leccion_hiragana.leccion
    fill_in "Significado", with: @leccion_hiragana.significado
    fill_in "Url Imagen", with: @leccion_hiragana.url_imagen
    click_on "Create Leccion hiragana"

    assert_text "Leccion hiragana was successfully created"
    click_on "Back"
  end

  test "updating a Leccion hiragana" do
    visit leccion_hiraganas_url
    click_on "Edit", match: :first

    fill_in "Hiragana", with: @leccion_hiragana.hiragana_id
    fill_in "Leccion", with: @leccion_hiragana.leccion
    fill_in "Significado", with: @leccion_hiragana.significado
    fill_in "Url Imagen", with: @leccion_hiragana.url_imagen
    click_on "Update Leccion hiragana"

    assert_text "Leccion hiragana was successfully updated"
    click_on "Back"
  end

  test "destroying a Leccion hiragana" do
    visit leccion_hiraganas_url
    page.accept_confirm do
      click_on "Destroy", match: :first
    end

    assert_text "Leccion hiragana was successfully destroyed"
  end
end
