class LeccionHiraganasController < ApplicationController
  before_action :set_leccion_hiragana, only: [:show, :edit, :update, :destroy]

  # GET /leccion_hiraganas
  # GET /leccion_hiraganas.json
  def index
    @leccion_hiraganas = LeccionHiragana.all
  end

  # GET /leccion_hiraganas/1
  # GET /leccion_hiraganas/1.json
  def show
  end

  # GET /leccion_hiraganas/new
  def new
    @leccion_hiragana = LeccionHiragana.new
  end

  # GET /leccion_hiraganas/1/edit
  def edit
  end

  # POST /leccion_hiraganas
  # POST /leccion_hiraganas.json
  def create
    @leccion_hiragana = LeccionHiragana.new(leccion_hiragana_params)

    respond_to do |format|
      if @leccion_hiragana.save
        format.html { redirect_to @leccion_hiragana, notice: 'Leccion hiragana was successfully created.' }
        format.json { render :show, status: :created, location: @leccion_hiragana }
      else
        format.html { render :new }
        format.json { render json: @leccion_hiragana.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /leccion_hiraganas/1
  # PATCH/PUT /leccion_hiraganas/1.json
  def update
    respond_to do |format|
      if @leccion_hiragana.update(leccion_hiragana_params)
        format.html { redirect_to @leccion_hiragana, notice: 'Leccion hiragana was successfully updated.' }
        format.json { render :show, status: :ok, location: @leccion_hiragana }
      else
        format.html { render :edit }
        format.json { render json: @leccion_hiragana.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /leccion_hiraganas/1
  # DELETE /leccion_hiraganas/1.json
  def destroy
    @leccion_hiragana.destroy
    respond_to do |format|
      format.html { redirect_to leccion_hiraganas_url, notice: 'Leccion hiragana was successfully destroyed.' }
      format.json { head :no_content }
    end
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_leccion_hiragana
      @leccion_hiragana = LeccionHiragana.find(params[:id])
    end

    # Never trust parameters from the scary internet, only allow the white list through.
    def leccion_hiragana_params
      params.require(:leccion_hiragana).permit(:leccion, :significado, :url_imagen, :hiragana_id)
    end
end
