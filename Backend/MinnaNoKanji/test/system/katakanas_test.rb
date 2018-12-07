require "application_system_test_case"

class KatakanasTest < ApplicationSystemTestCase
  setup do
    @katakana = katakanas(:one)
  end

  test "visiting the index" do
    visit katakanas_url
    assert_selector "h1", text: "Katakanas"
  end

  test "creating a Katakana" do
    visit katakanas_url
    click_on "New Katakana"

    fill_in "Explicacion", with: @katakana.explicacion
    fill_in "Leccion", with: @katakana.leccion
    click_on "Create Katakana"

    assert_text "Katakana was successfully created"
    click_on "Back"
  end

  test "updating a Katakana" do
    visit katakanas_url
    click_on "Edit", match: :first

    fill_in "Explicacion", with: @katakana.explicacion
    fill_in "Leccion", with: @katakana.leccion
    click_on "Update Katakana"

    assert_text "Katakana was successfully updated"
    click_on "Back"
  end

  test "destroying a Katakana" do
    visit katakanas_url
    page.accept_confirm do
      click_on "Destroy", match: :first
    end

    assert_text "Katakana was successfully destroyed"
  end
end
