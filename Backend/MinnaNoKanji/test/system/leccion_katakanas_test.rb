require "application_system_test_case"

class LeccionKatakanasTest < ApplicationSystemTestCase
  setup do
    @leccion_katakana = leccion_katakanas(:one)
  end

  test "visiting the index" do
    visit leccion_katakanas_url
    assert_selector "h1", text: "Leccion Katakanas"
  end

  test "creating a Leccion katakana" do
    visit leccion_katakanas_url
    click_on "New Leccion Katakana"

    fill_in "Katakana", with: @leccion_katakana.katakana_id
    fill_in "Leccion", with: @leccion_katakana.leccion
    fill_in "Significado", with: @leccion_katakana.significado
    fill_in "Url Imagen", with: @leccion_katakana.url_imagen
    click_on "Create Leccion katakana"

    assert_text "Leccion katakana was successfully created"
    click_on "Back"
  end

  test "updating a Leccion katakana" do
    visit leccion_katakanas_url
    click_on "Edit", match: :first

    fill_in "Katakana", with: @leccion_katakana.katakana_id
    fill_in "Leccion", with: @leccion_katakana.leccion
    fill_in "Significado", with: @leccion_katakana.significado
    fill_in "Url Imagen", with: @leccion_katakana.url_imagen
    click_on "Update Leccion katakana"

    assert_text "Leccion katakana was successfully updated"
    click_on "Back"
  end

  test "destroying a Leccion katakana" do
    visit leccion_katakanas_url
    page.accept_confirm do
      click_on "Destroy", match: :first
    end

    assert_text "Leccion katakana was successfully destroyed"
  end
end
